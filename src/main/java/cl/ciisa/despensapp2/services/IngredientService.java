package cl.ciisa.despensapp2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ciisa.despensapp2.model.Ingredient;
import cl.ciisa.despensapp2.model.Product;
import cl.ciisa.despensapp2.repository.IngredientRepository;
import jakarta.persistence.EntityNotFoundException;


@Service
public class IngredientService {

	@Autowired
	private IngredientRepository ingredientRepository;
	
	@Autowired
	private ProductService productService;

	public List<Ingredient> findAll() {
	    return ingredientRepository.findAll();
	}

	public Optional<Ingredient> findById(Long id) {
	    return ingredientRepository.findById(id);
	}

	public Ingredient save(Ingredient ingredient) {
	    return ingredientRepository.save(ingredient);
	}

	public void deleteById(Long id) {
	    ingredientRepository.deleteById(id);
	}

	public List<Ingredient> findByRecipeId(Long recipeId) {
	    return ingredientRepository.findByRecipeId(recipeId);
	}

	public List<Ingredient> findByProduct(Product product) {
	    return ingredientRepository.findByProduct(product);
	}
	
	public boolean checkIngredientQuantity(Long productId, Long pantryId, int requiredQuantity) {
	    try {
	        
			int pantryQuantity = productService.getQuantityFromProductPantry(productId, pantryId);
	        if (pantryQuantity >= requiredQuantity) {
	            return true;
	        } else {
	            return false;
	        }
	    } catch (EntityNotFoundException e) {
	        return false;
	    }
	}
	
	public Long countIngredientsByRecipeId(Long recipeId) {
	    return ingredientRepository.countByRecipeId(recipeId);
	}
	
}