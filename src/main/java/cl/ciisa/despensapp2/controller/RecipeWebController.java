package cl.ciisa.despensapp2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//import cl.ciisa.despensapp2.model.Ingredient;
import cl.ciisa.despensapp2.model.Recipe;
import cl.ciisa.despensapp2.model.dto.IngredientProductDTO;
import cl.ciisa.despensapp2.services.RecipeService;

@Controller
public class RecipeWebController {

	@Autowired
	private RecipeService recipeService;

	@GetMapping("/recipes/{id}")
	public String showRecipe(@PathVariable Long id, Model model) {
	    Recipe recipe = recipeService.getRecipeById(id);
	    if (recipe != null) {
	        List<IngredientProductDTO> ingredientDTOs = recipeService.getIngredientsForRecipe(id);
	        model.addAttribute("recipe", recipe);
	        model.addAttribute("ingredientDTOs", ingredientDTOs);
	    }
	    return "recipe-detail";
	}
}
