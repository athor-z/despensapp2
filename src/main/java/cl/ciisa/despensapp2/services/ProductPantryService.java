package cl.ciisa.despensapp2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ciisa.despensapp2.model.ProductPantry;
import cl.ciisa.despensapp2.model.ProductPantryId;
import cl.ciisa.despensapp2.repository.ProductPantryRepository;

@Service
public class ProductPantryService {
	
    @Autowired
    private ProductPantryRepository productPantryRepository;

    public ProductPantry save(ProductPantry productPantry) {
        return productPantryRepository.save(productPantry);
    }

    public void delete(ProductPantryId id) {
        productPantryRepository.deleteById(id);
    }

    public List<ProductPantry> getAll() {
        return productPantryRepository.findAll();
    }

    public ProductPantry getById(ProductPantryId id) {
        return productPantryRepository.findById(id).orElse(null);
    }
    //NUEVO
    public long countProductsInPantryByUsername(String username) {
        return productPantryRepository.countByPantryUserUsername(username);
    }
    //Otro NUEVO
    public List<ProductPantry> getProductsInPantryByUsername(String username) {
        return productPantryRepository.findByPantryUserUsername(username);
    }
    //public long getCountOfProductsInPantryByUsername(String username) {
    //    return productPantryRepository.countByUserUsername(username);
    //}
}