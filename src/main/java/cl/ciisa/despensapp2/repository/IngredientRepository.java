package cl.ciisa.despensapp2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.ciisa.despensapp2.model.Ingredient;
import cl.ciisa.despensapp2.model.Product;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

	List<Ingredient> findByRecipeId(Long recipeId);

	List<Ingredient> findByProduct(Product product);

	Long countByRecipeId(Long recipeId);
	
    
}