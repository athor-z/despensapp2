package cl.ciisa.despensapp2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.ciisa.despensapp2.model.ProductPantry;
import cl.ciisa.despensapp2.model.ProductPantryId;

@Repository
public interface ProductPantryRepository extends JpaRepository<ProductPantry, ProductPantryId> {
	//long countProductsInPantryByUsername(String username);
	//long countByUserUsername(String username);
	long countByPantryUserUsername(String username);
	List<ProductPantry> findByPantryUserUsername(String username);
	//Con JPQL Sí funciona
	@Query("SELECT pp FROM ProductPantry pp WHERE pp.pantry.user.id = :userId AND pp.product.id = :productId")
	Optional<ProductPantry> findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
	List<ProductPantry> findByPantryIdAndProductIdIn(Long pantryId, List<Long> productIds);
}