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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.ciisa.despensapp2.model.Pantry;
import cl.ciisa.despensapp2.model.Product;
import cl.ciisa.despensapp2.model.ProductPantry;
import cl.ciisa.despensapp2.model.ProductPantryId;
import cl.ciisa.despensapp2.services.PantryService;
import cl.ciisa.despensapp2.services.ProductPantryService;
import cl.ciisa.despensapp2.services.ProductService;

@RestController
@RequestMapping("/productpantry")
public class ProductPantryController {

    @Autowired
    private ProductPantryService productPantryService;
    @Autowired
    private PantryService pantryService;
    @Autowired
    private ProductService productService;
    

    @PostMapping
    public ResponseEntity<ProductPantry> addProductPantry(@RequestBody ProductPantry productPantry) {
        Pantry pantry = productPantry.getId().getPantry();
        Product product = productPantry.getId().getProduct();

		pantry = pantryService.findById(pantry.getId()).get();
        product = productService.findById(product.getId()).get();

        if (pantry == null || product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productPantry.getId().setPantry(pantry);
        productPantry.getId().setProduct(product);

        ProductPantry newProductPantry = productPantryService.save(productPantry);
        return new ResponseEntity<>(newProductPantry, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/pantry/{pantryId}/product/{productId}") //Ejemplo solic. HTTP /productpantry/pantry/pantry_id/product/product_id
    public ResponseEntity<Void> deleteProductPantry(@PathVariable("pantryId") Long pantryId, @PathVariable("productId") Long productId) {
        Pantry pantry = new Pantry();
        pantry.setId(pantryId);

        Product product = new Product();
        product.setId(productId);

        ProductPantryId id = new ProductPantryId();
        id.setPantry(pantry);
        id.setProduct(product);

        productPantryService.delete(id);

        return ResponseEntity.ok().build();
    }
    
    
    @PutMapping("/update-quantity/pantry/{pantryId}/product/{productId}")
    public ResponseEntity<ProductPantry> updateProductPantryQuantity(@PathVariable("pantryId") Long pantryId, 
                                                                     @PathVariable("productId") Long productId,
                                                                     /*@RequestBody Integer quantity)*/
                                                                     @RequestParam(required = false) Integer quantity)
                                                                     {
        Pantry pantry = pantryService.findById(pantryId).get();
        if (pantry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Product product = productService.findById(productId).get();
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ProductPantryId id = new ProductPantryId(pantry, product);
        ProductPantry productPantry = productPantryService.getById(id);

        if (productPantry != null) {
            productPantry.setQuantity(quantity);
            ProductPantry updatedProductPantry = productPantryService.save(productPantry);
            return new ResponseEntity<>(updatedProductPantry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductPantry>> getAllProductPantry() {
        List<ProductPantry> productPantryList = productPantryService.getAll();
        for (ProductPantry productPantry : productPantryList) {
            Pantry pantry = pantryService.findById(productPantry.getId().getPantry().getId()).get();
            Product product = productService.findById(productPantry.getId().getProduct().getId()).get();
            productPantry.getId().setPantry(pantry);
            productPantry.getId().setProduct(product);
        }
        return new ResponseEntity<>(productPantryList, HttpStatus.OK);
    }

    @GetMapping("/find/pantry/{pantryId}/product/{productId}")
    public ResponseEntity<ProductPantry> getProductPantryById(@PathVariable("pantryId") Long pantryId, @PathVariable("productId") Long productId) {
        Pantry pantry = pantryService.findById(pantryId).orElse(null);
        Product product = productService.findById(productId).orElse(null);

        if (pantry != null && product != null) {
            ProductPantryId id = new ProductPantryId();
            id.setPantry(pantry);
            id.setProduct(product);

            ProductPantry productPantry = productPantryService.getById(id);

            if (productPantry != null) {
                return new ResponseEntity<>(productPantry, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}