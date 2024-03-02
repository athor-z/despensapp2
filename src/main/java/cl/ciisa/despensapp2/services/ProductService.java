package cl.ciisa.despensapp2.services;

import cl.ciisa.despensapp2.model.Pantry;
import cl.ciisa.despensapp2.model.Product;
import cl.ciisa.despensapp2.model.ProductPantry;
import cl.ciisa.despensapp2.model.ProductPantryId;
import cl.ciisa.despensapp2.repository.PantryRepository;
import cl.ciisa.despensapp2.repository.ProductPantryRepository;
import cl.ciisa.despensapp2.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductPantryRepository productPantryRepository;
    private final PantryRepository pantryRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
    
    public int getQuantityFromProductPantry(Long productId, Long pantryId) {
        ProductPantryId productPantryId = new ProductPantryId();
        Pantry pantry = pantryRepository.findById(pantryId)
                .orElseThrow(() -> new EntityNotFoundException("Pantry with id " + pantryId + " not found"));
        productPantryId.setPantry(pantry);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));
        productPantryId.setProduct(product);
        ProductPantry productPantry = productPantryRepository.findById(productPantryId)
                .orElseThrow(() -> new EntityNotFoundException("Product pantry with id " + productPantryId + " not found"));
        return productPantry.getQuantity();
    }
    
    public Long getCountOfProducts() {
        return productRepository.count();
    }
    
    //01-03-24
    //Actualizar la cantidad de producto en despensa al preparar la RECETA, esto propablemente no se use (reemplaza IngredientService)
    public void updateProductPantryQuantity(Long productId, Long pantryId, int quantityChange) {
        ProductPantryId productPantryId = new ProductPantryId();
        Pantry pantry = pantryRepository.findById(pantryId)
                .orElseThrow(() -> new EntityNotFoundException("Pantry with id " + pantryId + " not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));

        productPantryId.setPantry(pantry);
        productPantryId.setProduct(product);

        ProductPantry productPantry = productPantryRepository.findById(productPantryId)
                .orElseThrow(() -> new EntityNotFoundException("ProductPantry not found for given product and pantry IDs"));

        int updatedQuantity = productPantry.getQuantity() + quantityChange;
        if (updatedQuantity < 0) {
            throw new IllegalArgumentException("Cannot have negative quantity in pantry");
        }

        productPantry.setQuantity(updatedQuantity);
        productPantryRepository.save(productPantry);
    }

    
}