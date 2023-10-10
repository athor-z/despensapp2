package cl.ciisa.despensapp2.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cl.ciisa.despensapp2.model.Product;
import cl.ciisa.despensapp2.model.Recipe;
import cl.ciisa.despensapp2.services.ProductPantryService;
import cl.ciisa.despensapp2.services.ProductService;
import cl.ciisa.despensapp2.services.RecipeService;
import cl.ciisa.despensapp2.services.UserService;

@Controller
public class HomeWebController {
//NOTA: Los Model se deben añadir en los controladores de las vistas para que los valores sean mostrados en la pagina (FrontEnd)
    @Autowired
    private ProductService productService;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private ProductPantryService productPantryService;
    @Autowired
    private UserService userService;
    //@Autowired
    //private ProductPantryService productPantryService; 
    
    @GetMapping("/")
    public String home(Model model, Principal principal) {
    	String username = principal.getName();
    	String userEmail = userService.findEmailByUsername(username);
    	List<Recipe> allRecipes = recipeService.getAllRecipes();
    	
        // Obtén las últimas 5 recetas (o el número deseado)
        int numberOfLatestRecipes = 5;
        List<Recipe> latestRecipes = allRecipes.subList(0, Math.min(numberOfLatestRecipes, allRecipes.size()));
    	
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
        
        return "index"; // Aquí devuelves el nombre de la plantilla Thymeleaf para la página de inicio
    }
}
