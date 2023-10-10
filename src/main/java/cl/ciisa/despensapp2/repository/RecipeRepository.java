package cl.ciisa.despensapp2.repository;

import cl.ciisa.despensapp2.model.FoodRestriction;
import cl.ciisa.despensapp2.model.Recipe;
import cl.ciisa.despensapp2.model.RecipeDifficulty;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

	List<Recipe> findByDifficulty(RecipeDifficulty difficulty);

	List<Recipe> findByRestriction(FoodRestriction restriction);
	
	//List<Recipe> findTopNByOrderByIdDesc(int count);
}
