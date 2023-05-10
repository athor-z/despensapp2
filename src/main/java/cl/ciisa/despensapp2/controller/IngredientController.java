package cl.ciisa.despensapp2.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.ciisa.despensapp2.model.Ingredient;
import cl.ciisa.despensapp2.model.Product;
import cl.ciisa.despensapp2.model.Recipe;
import cl.ciisa.despensapp2.services.IngredientService;
import cl.ciisa.despensapp2.services.ProductService;
import cl.ciisa.despensapp2.services.RecipeService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
	
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Ingredient> getAllIngredients() {
        return ingredientService.findAll();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable Long id) {
        Optional<Ingredient> ingredient = ingredientService.findById(id);
        return ingredient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        Recipe recipe = recipeService.getRecipeById(ingredient.getRecipe().getId());
                //.orElseThrow(() -> new ResourceNotFoundException("Recipe not found with id " + ingredient.getRecipe().getId()));
        Product product = productService.findById(ingredient.getProduct().getId()).get();
                //.orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + ingredient.getProduct().getId()));
        ingredient.setRecipe(recipe);
        ingredient.setProduct(product);
        return ResponseEntity.ok(ingredientService.save(ingredient));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable Long id, @RequestBody Ingredient updatedIngredient) {
        Optional<Ingredient> ingredient = ingredientService.findById(id);
        if (ingredient.isPresent()) {
            Ingredient savedIngredient = ingredient.get();
            savedIngredient.setProduct(updatedIngredient.getProduct());
            savedIngredient.setQuantity(updatedIngredient.getQuantity());
            savedIngredient.setRecipe(updatedIngredient.getRecipe());
            return ResponseEntity.ok(ingredientService.save(savedIngredient));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        Optional<Ingredient> ingredient = ingredientService.findById(id);
        if (ingredient.isPresent()) {
            ingredientService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/recipe/{recipeId}")
    public List<Ingredient> getIngredientsByRecipeId(@PathVariable Long recipeId) {
        return ingredientService.findByRecipeId(recipeId);
    }

    @GetMapping("/product")
    public List<Ingredient> getIngredientsByProduct(@RequestBody Product product) {
        return ingredientService.findByProduct(product);
    }

    @GetMapping("/recipe/{recipeId}/count")
    public Long countIngredientsByRecipeId(@PathVariable Long recipeId) {
        return ingredientService.countIngredientsByRecipeId(recipeId);
    }
   
    @GetMapping("/checkIngredients/{recipeId}")
    public ResponseEntity<String> checkIngredientQuantity(@PathVariable Long recipeId, @RequestParam Long pantryId) {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }

        List<Ingredient> ingredients = ingredientService.findByRecipeId(recipeId);
        for (Ingredient ingredient : ingredients) {
            try {
                int quantity = productService.getQuantityFromProductPantry(ingredient.getProduct().getId(), pantryId);
                if (quantity < ingredient.getQuantity()) {
                    return ResponseEntity.badRequest().body("Te falta(n) "+(ingredient.getQuantity()-quantity)+" '" + ingredient.getProduct().getName() + "(s)' en la despensa para preparar esta receta");
                }
            } catch (EntityNotFoundException ex) {
                return ResponseEntity.badRequest().body("No hay '" + ingredient.getProduct().getName() + "' en la despensa para preparar '"+recipe.getName()+"'");
            }
        }

        return ResponseEntity.ok("Felicidades! tienes todos los ingredientes necesarios para la receta.");
    }
}