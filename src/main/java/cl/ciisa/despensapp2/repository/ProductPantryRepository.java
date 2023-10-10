package cl.ciisa.despensapp2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.ciisa.despensapp2.model.ProductPantry;
import cl.ciisa.despensapp2.model.ProductPantryId;

@Repository
public interface ProductPantryRepository extends JpaRepository<ProductPantry, ProductPantryId> {
	//long countProductsInPantryByUsername(String username);
	//long countByUserUsername(String username);
	//NUEVO
	long countByPantryUserUsername(String username);
	//Otro NUEVO
	List<ProductPantry> findByPantryUserUsername(String username);
}