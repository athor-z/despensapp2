package cl.ciisa.despensapp2.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cl.ciisa.despensapp2.model.Product;
import cl.ciisa.despensapp2.services.ProductPantryService;
import cl.ciisa.despensapp2.services.ProductService;

@Controller
public class ProductWebController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductPantryService productPantryService;
    
    @GetMapping("/products")
    public String productList(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "pages-products"; // Nombre de la plantilla Thymeleaf para la página de listado de productos
    }
    
    @PostMapping("/addToPantryFromProductList")
    public String addToPantry(@RequestParam Long productId, @RequestParam int quantity, Authentication authentication) {
        String username = authentication.getName();
        productPantryService.addProductToPantry(username, productId, quantity);
        return "redirect:/products";
    }

}
