package cl.ciisa.despensapp2.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.ciisa.despensapp2.model.Product;
import cl.ciisa.despensapp2.model.Recipe;
import cl.ciisa.despensapp2.model.User;
import cl.ciisa.despensapp2.model.dto.IngredientProductDTO;
import cl.ciisa.despensapp2.model.dto.MissingIngredientDTO;
import cl.ciisa.despensapp2.model.dto.PantryItemDTO;
import cl.ciisa.despensapp2.services.IngredientAvailability;
import cl.ciisa.despensapp2.services.PantryService;
import cl.ciisa.despensapp2.services.ProductPantryService;
import cl.ciisa.despensapp2.services.ProductService;
import cl.ciisa.despensapp2.services.RecipeService;
import cl.ciisa.despensapp2.services.UserService;

@Controller
public class RecipeWebController {

	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PantryService pantryService;
	
	 @Autowired
    private ProductService productService;
   
    @Autowired
    private ProductPantryService productPantryService;
   
    
	@GetMapping("/recipes/{id}")
	public String showRecipe(@PathVariable Long id, Model model, Principal principal) {
	    Recipe recipe = recipeService.getRecipeById(id);
	    
	    // Obtiene el nombre de usuario del usuario autenticado
	    String username = principal.getName();
	    Long userId = userService.findUserIdByUsername(username);
	    
	    if (recipe != null) {
	    	
	    	// Obtener IngredientProductDTOs para la receta, que incluyen la cantidad requerida
	        List<IngredientProductDTO> ingredientDTOs = recipeService.getIngredientsForRecipe(id);
	        // Crear un mapa de las cantidades requeridas para cada ingrediente/producto
	        Map<Long, Integer> IngredientProductRequiredQuantities = ingredientDTOs.stream()
	                .collect(Collectors.toMap(IngredientProductDTO::getId, IngredientProductDTO::getQuantity));
	        // Usa método que devuelve los contenidos/articulos de la despensa del usuario		
	        List<PantryItemDTO> pantryContents = pantryService.getPantryContentsForRecipeIngredients(userId,IngredientProductRequiredQuantities);
	        
	        model.addAttribute("recipe", recipe); //Contenido de Receta
	        model.addAttribute("ingredientDTOs", ingredientDTOs); //Lista de Ingredientes necesarios para la receta
	        model.addAttribute("pantryContents", pantryContents); //Lista de Productos en despensa de usuario

	        IngredientAvailability availability = recipeService.checkIngredientsAvailability(id, userId);
	        model.addAttribute("ingredientAvailabilityStatus", availability);
	        
            if (availability == IngredientAvailability.PARTIAL) {
                List<MissingIngredientDTO> missingIngredients = recipeService.getMissingIngredients(id, userId); //Esto debe listar los ingredientes faltantes del usuario
                model.addAttribute("missingIngredients", missingIngredients);
            }
	    }
	    return "recipe-detail";
	}
	
	@PostMapping("/recipes/{recipeId}/prepare")
	public String prepareRecipe(@PathVariable Long recipeId, Authentication authentication, RedirectAttributes redirectAttributes) {
	    try {
	        User user = (User) authentication.getPrincipal();
	        Long userId = user.getId();

	        IngredientAvailability availabilityStatus = recipeService.checkIngredientsAvailability(recipeId, userId);

	        switch (availabilityStatus) {
	            case COMPLETE:
	                recipeService.prepareRecipe(recipeId, userId); // Este método ahora maneja la lógica de actualización de la despensa --FUNCIONANDO
	                redirectAttributes.addFlashAttribute("successMessage", "La receta se ha preparado exitosamente y tu despensa ha sido actualizada.");
	                break;
	            case PARTIAL:
	                redirectAttributes.addFlashAttribute("errorMessage", "Algunos ingredientes no están disponibles en la cantidad necesaria.");
	                break;
	            case NONE:
	                redirectAttributes.addFlashAttribute("errorMessage", "No tienes ninguno de los ingredientes necesarios en tu despensa.");
	                break;
	        }

	        return "redirect:/recipes/" + recipeId;
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Error al preparar la receta: " + e.getMessage());
	        return "redirect:/recipes/" + recipeId;
	    }
	}

	@GetMapping("/recipe-list")
    public String showAllRecipe(Model model, Principal principal) {
    	String username = principal.getName();
    	String userEmail = userService.findEmailByUsername(username);
    	List<Recipe> allRecipes = recipeService.getAllRecipes();
    	
       
        List<Recipe> latestRecipes = allRecipes;
    	
    	long productPantryCount = productPantryService.countProductsInPantryByUsername(username);
    	
    	long productCount = productService.getCountOfProducts();
        List<Product> products = productService.findAll();
        long recipeCount = recipeService.getCountOfRecipes();
        List<Recipe> recipes = recipeService.getAllRecipes();
        
        
        model.addAttribute("productCount", productCount);
        model.addAttribute("products", products);
        model.addAttribute("recipeCount", recipeCount);
        model.addAttribute("recipes", recipes);
        model.addAttribute("productPantryCount", productPantryCount);
        model.addAttribute("username",username);
        model.addAttribute("userEmail",userEmail);
        model.addAttribute("latestRecipes", latestRecipes); //ultimas recetas
        
        return "pages-recipes"; // Aquí devuelves el nombre de la plantilla Thymeleaf para la página de inicio
    }

	@GetMapping("/search")
    public String searchRecipes(@RequestParam("keyword") String keyword, Model model) {
        List<Recipe> recipes = recipeService.searchRecipesByKeyword(keyword);
        model.addAttribute("recipes", recipes);
        return "recipe_search_results"; // Nombre de la vista donde se mostrarán los resultados
    }
    //Autocompletar en búsqueda
    @GetMapping("/autocomplete")
    @ResponseBody
    public List<Map<String, Object>> autocompleteRecipes(@RequestParam("keyword") String keyword) {
        List<Recipe> recipes = recipeService.searchRecipesByKeyword(keyword);
        
        // Convertimos las recetas a un formato más sencillo para enviar como JSON
        return recipes.stream()
                .map(recipe -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", recipe.getId());
                    map.put("name", recipe.getName());
                    return map;
                })
                .collect(Collectors.toList());
    }


}
