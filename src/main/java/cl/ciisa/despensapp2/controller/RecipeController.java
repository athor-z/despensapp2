package cl.ciisa.despensapp2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ciisa.despensapp2.model.FoodRestriction;
import cl.ciisa.despensapp2.model.Recipe;
import cl.ciisa.despensapp2.model.RecipeDifficulty;
import cl.ciisa.despensapp2.services.RecipeService;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    
    @Autowired
    private RecipeService recipeService;
    
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }
    
    @GetMapping("/find/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        Recipe recipe = recipeService.getRecipeById(id);
        if (recipe == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(recipe, HttpStatus.OK);
        }
    }
    
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        Recipe createdRecipe = recipeService.createRecipe(recipe);
        return new ResponseEntity<>(createdRecipe, HttpStatus.CREATED);
    }
    
    //Habrá que ver los parámetros por separado despues...
    @PutMapping("/update/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        Recipe updatedRecipe = recipeService.updateRecipe(id, recipe);
        if (updatedRecipe == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        boolean deleted = recipeService.deleteRecipe(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/find/difficulty/{difficulty}")
    public ResponseEntity<List<Recipe>> getRecipesByDifficulty(@PathVariable RecipeDifficulty difficulty) {
        List<Recipe> recipes = recipeService.getRecipesByDifficulty(difficulty);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }
    
    @GetMapping("/find/restriction/{restriction}")
    public ResponseEntity<List<Recipe>> getRecipesByRestriction(@PathVariable FoodRestriction restriction) {
        List<Recipe> recipes = recipeService.getRecipesByRestriction(restriction);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }
    
}