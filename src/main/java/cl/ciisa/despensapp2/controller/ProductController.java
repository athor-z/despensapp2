package cl.ciisa.despensapp2.controller;

import cl.ciisa.despensapp2.model.Product;
//import cl.ciisa.despensapp2.repository.ProductRepository;
import cl.ciisa.despensapp2.services.ProductService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products-rest")
public class ProductController {

    private final ProductService productService;
    //private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = productService.findAll();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id).get();
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product newProduct = productService.save(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product product = productService.findById(id).get();
        product.setName(updatedProduct.getName());
        //product.setDescription(updatedProduct.getDescription());
        product.setMeasureUnit(updatedProduct.getMeasureUnit());
        Product savedProduct = productService.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    /*public Long getCountOfProducts() {
        return productRepository.count();
    }*/
    /*@GetMapping("/count")
    public String countProducts(Model model) {
        long productCount = productService.getCountOfProducts();
        model.addAttribute("productCount", productCount);
        return "your-view-name"; // Reemplaza "your-view-name" por el nombre de tu vista Thymeleaf
    }*/
}
