package cl.ciisa.despensapp2.services;

import cl.ciisa.despensapp2.model.FoodRestriction;
import cl.ciisa.despensapp2.model.Ingredient;
import cl.ciisa.despensapp2.model.Product;
import cl.ciisa.despensapp2.model.ProductPantry;
import cl.ciisa.despensapp2.model.Recipe;
import cl.ciisa.despensapp2.model.RecipeDifficulty;
import cl.ciisa.despensapp2.model.User;
import cl.ciisa.despensapp2.model.dto.IngredientProductDTO;
import cl.ciisa.despensapp2.model.dto.MissingIngredientDTO;
import cl.ciisa.despensapp2.repository.IngredientRepository;
import cl.ciisa.despensapp2.repository.ProductPantryRepository;
import cl.ciisa.despensapp2.repository.RecipeRepository;
import cl.ciisa.despensapp2.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    
	@Autowired
	private RecipeRepository recipeRepository;
	//01-03-24
	@Autowired
	private IngredientRepository ingredientRepository;
	//03-04-24
	@Autowired
	private ProductPantryRepository productPantryRepository;
	@Autowired
	private UserRepository userRepository;
	
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
	
    public Long getCountOfRecipes() {
        return recipeRepository.count();
    }
    
    /*
    public List<Recipe> getLatestRecipes(int count) {
        // Utiliza el repositorio para obtener las últimas recetas
        return recipeRepository.findTopNByOrderByIdDesc(count);
    }
    */
    //01-03-24
    public List<Ingredient> getIngredientsByRecipeId(Long recipeId) {
        return ingredientRepository.findByRecipeId(recipeId);
    }
    
    //02-03-24 -> Explotando la DTO
    public List<IngredientProductDTO> getIngredientsForRecipe(Long recipeId) {
        List<Ingredient> ingredients = ingredientRepository.findByRecipeId(recipeId);
        return ingredients.stream()
                .map(ingredient -> new IngredientProductDTO(ingredient.getProduct().getId(),ingredient.getProduct().getName(),ingredient.getQuantity(),ingredient.getProduct().getMeasureUnit()))
                .collect(Collectors.toList());
    }
    
    //03-03-24 Funcionalidades de verificacion y descuento de ingredientes
    //Verificar Ingredientes en Despensa --YA FUNCIONA
   
    public IngredientAvailability checkIngredientsAvailability(Long recipeId, Long userId) {
        List<Ingredient> ingredients = ingredientRepository.findByRecipeId(recipeId);
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return IngredientAvailability.NONE;

        boolean allAvailable = true;
        boolean noneAvailable = true;
        
        for (Ingredient ingredient : ingredients) {
            Optional<ProductPantry> productPantryOpt = productPantryRepository.findByUserIdAndProductId(userId, ingredient.getProduct().getId());
            if (productPantryOpt.isPresent()) {
                ProductPantry productPantry = productPantryOpt.get();
                if (productPantry.getQuantity() < ingredient.getQuantity()) {
                    allAvailable = false;
                } else {
                    noneAvailable = false;
                }
            } else {
                allAvailable = false;
            }
        }
        
        if (noneAvailable) return IngredientAvailability.NONE;
        return allAvailable ? IngredientAvailability.COMPLETE : IngredientAvailability.PARTIAL;
    }
    
    //Preparar Receta --YA FUNCIONA
    
    @Transactional
    public boolean prepareRecipe(Long recipeId, Long userId) {
        if (checkIngredientsAvailability(recipeId, userId) != IngredientAvailability.COMPLETE) {
            return false;
        }

        List<Ingredient> ingredients = ingredientRepository.findByRecipeId(recipeId);
        for (Ingredient ingredient : ingredients) {
            ProductPantry productPantry = productPantryRepository.findByUserIdAndProductId(userId, ingredient.getProduct().getId()).orElse(null);
            if (productPantry != null && productPantry.getQuantity() >= ingredient.getQuantity()) {
                productPantry.setQuantity(productPantry.getQuantity() - ingredient.getQuantity());
                productPantryRepository.save(productPantry);
            }
        }
        return true;
    }
    
    // Método para obtener los ingredientes que faltan para una receta dada un ID de receta y un ID de usuario
    //Esto está Funcionando bien...
    /*
    public List<String> getMissingIngredients(Long recipeId, Long userId) {
        List<Ingredient> ingredients = ingredientRepository.findByRecipeId(recipeId);
        return ingredients.stream()
                .filter(ingredient -> {
                    Optional<ProductPantry> productPantryOpt = productPantryRepository.findByUserIdAndProductId(userId, ingredient.getProduct().getId());
                    return productPantryOpt.isEmpty() || productPantryOpt.get().getQuantity() < ingredient.getQuantity();
                })
                .map(ingredient -> ingredient.getProduct().getName())
                .collect(Collectors.toList());
    }
    */
    public List<MissingIngredientDTO> getMissingIngredients(Long recipeId, Long userId) {
        List<Ingredient> ingredients = ingredientRepository.findByRecipeId(recipeId);
        return ingredients.stream()
                .filter(ingredient -> {
                    Optional<ProductPantry> productPantryOpt = productPantryRepository.findByUserIdAndProductId(userId, ingredient.getProduct().getId());
                    return productPantryOpt.isEmpty() || productPantryOpt.get().getQuantity() < ingredient.getQuantity();
                })
                .map(ingredient -> new MissingIngredientDTO(
                    ingredient.getProduct().getName(),
                    ingredient.getProduct().getMeasureUnit() // No es necesario llamar a .name() ya que MeasureUnit es el tipo esperado
                ))
                .collect(Collectors.toList());
    }
    //NUEVO 05-03-24 Convertir a IngredientDTO, es casi lo mismo que getIngredientsForRecipe
    public List<IngredientProductDTO> convertToIngredientDTOs(List<Ingredient> ingredients) {
        return ingredients.stream().map(ingredient -> {
            Product product = ingredient.getProduct(); // Asume que Ingredient tiene una referencia a Product
            return new IngredientProductDTO(
            		product.getId(),
                    product.getName(),
                    ingredient.getQuantity(),
                    product.getMeasureUnit()
            );
        }).collect(Collectors.toList());
    }

}
