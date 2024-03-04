package cl.ciisa.despensapp2.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.ciisa.despensapp2.model.Recipe;
import cl.ciisa.despensapp2.model.dto.IngredientProductDTO;
import cl.ciisa.despensapp2.services.IngredientAvailability;
import cl.ciisa.despensapp2.services.RecipeService;
import cl.ciisa.despensapp2.services.UserService;

@Controller
public class RecipeWebController {

	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	UserService userService;
	
    
	@GetMapping("/recipes/{id}")
	public String showRecipe(@PathVariable Long id, Model model, Principal principal) {
	    Recipe recipe = recipeService.getRecipeById(id);
	    
	    if (recipe != null) {
	        List<IngredientProductDTO> ingredientDTOs = recipeService.getIngredientsForRecipe(id);
	        model.addAttribute("recipe", recipe);
	        model.addAttribute("ingredientDTOs", ingredientDTOs);
	        //04-03-24
	        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String username = principal.getName(); // Obtiene el nombre de usuario del usuario autenticado
	        Long userId = userService.findUserIdByUsername(username);
	        IngredientAvailability availability = recipeService.checkIngredientsAvailability(id, userId);
	        model.addAttribute("ingredientAvailabilityStatus", availability);
	        
            if (availability == IngredientAvailability.PARTIAL) {
                List<String> missingIngredients = recipeService.getMissingIngredients(id, userId); //Esto debe listar los ingredientes faltantes del usuario
                model.addAttribute("missingIngredients", missingIngredients);
            }
	    }
	    return "recipe-detail";
	}
	
	//02-03-24
	/*
	@PostMapping("/recipes/{id}/prepare")
	public String prepareRecipe(@PathVariable Long id, RedirectAttributes redirectAttrs) {
	    boolean success = recipeService.prepareRecipe(id, getCurrentUserId());
	    if (success) {
	        redirectAttrs.addFlashAttribute("successMessage", "Recipe prepared successfully!");
	    } else {
	        redirectAttrs.addFlashAttribute("errorMessage", "Failed to prepare the recipe due to insufficient ingredients.");
	    }
	    return "redirect:/recipes/{id}";
	}
	*/
}
