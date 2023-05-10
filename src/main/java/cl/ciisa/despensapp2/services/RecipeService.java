package cl.ciisa.despensapp2.services;

import cl.ciisa.despensapp2.model.FoodRestriction;
import cl.ciisa.despensapp2.model.Recipe;
import cl.ciisa.despensapp2.model.RecipeDifficulty;
import cl.ciisa.despensapp2.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    
	@Autowired
	private RecipeRepository recipeRepository;

	public List<Recipe> getAllRecipes() {
	    return recipeRepository.findAll();
	}

	public Recipe getRecipeById(Long id) {
	    Optional<Recipe> recipe = recipeRepository.findById(id);
	    return recipe.orElse(null);
	}

	public Recipe createRecipe(Recipe recipe) {
	    return recipeRepository.save(recipe);
	}

	public Recipe updateRecipe(Long id, Recipe recipe) {
	    Recipe updatedRecipe = getRecipeById(id);
	    if (updatedRecipe == null) {
	        return null;
	    }
	    updatedRecipe.setName(recipe.getName());
	    updatedRecipe.setImage(recipe.getImage());
	    updatedRecipe.setDescription(recipe.getDescription());
	    updatedRecipe.setDifficulty(recipe.getDifficulty());
	    updatedRecipe.setRestriction(recipe.getRestriction());
	    return recipeRepository.save(updatedRecipe);
	}

	public boolean deleteRecipe(Long id) {
	    Recipe recipe = getRecipeById(id);
	    if (recipe == null) {
	        return false;
	    }
	    recipeRepository.delete(recipe);
	    return true;
	}

	public List<Recipe> getRecipesByDifficulty(RecipeDifficulty difficulty) {
	    return recipeRepository.findByDifficulty(difficulty);
	}

	public List<Recipe> getRecipesByRestriction(FoodRestriction restriction) {
	    return recipeRepository.findByRestriction(restriction);
	}
}
