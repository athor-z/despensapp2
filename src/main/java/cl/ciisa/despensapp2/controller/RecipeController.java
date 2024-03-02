package cl.ciisa.despensapp2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.ciisa.despensapp2.model.Ingredient;
import cl.ciisa.despensapp2.model.Recipe;
import cl.ciisa.despensapp2.services.RecipeService;

@Controller
public class RecipeController {

	@Autowired
	private RecipeService recipeService;

	@GetMapping("/recipes/{id}")
	public String viewRecipe(@PathVariable Long id, Model model) {
		Recipe recipe = recipeService.getRecipeById(id);
		List<Ingredient> ingredients = recipeService.getIngredientsByRecipeId(id);; //NEW 01-03-24
		model.addAttribute("recipe", recipe);
		model.addAttribute("ingredients", ingredients); //NEW 01-03-24
		return "recipe-detail"; // Nombre de la plantilla Thymeleaf para la página de detalle de receta
	}
}
