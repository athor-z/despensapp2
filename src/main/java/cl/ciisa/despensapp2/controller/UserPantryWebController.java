package cl.ciisa.despensapp2.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.ciisa.despensapp2.model.ProductPantryId;
import cl.ciisa.despensapp2.model.dto.ProductPantryDTO;
import cl.ciisa.despensapp2.repository.PantryRepository;
import cl.ciisa.despensapp2.repository.ProductRepository;
import cl.ciisa.despensapp2.services.PantryService;
import cl.ciisa.despensapp2.services.ProductPantryService;
import cl.ciisa.despensapp2.services.UserService;

@Controller
public class UserPantryWebController {

    @Autowired
    private ProductPantryService productPantryService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private PantryService pantryService;
    
    @Autowired
    private PantryRepository pantryRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/pantry")
    public String userPantry(Model model, Principal principal) {
        String username = principal.getName();
        String userEmail = userService.findEmailByUsername(username);
        // Obtén el nombre de la despensa del usuario
        String pantryName = pantryService.getPantryNameByUsername(username);
        // Obtener la información de la despensa del usuario como DTOs
        List<ProductPantryDTO> userPantryList = productPantryService.getProductPantryDTOsByUsername(username);

        model.addAttribute("userEmail", userEmail);
        model.addAttribute("userPantryList", userPantryList);
        model.addAttribute("userPantryName", pantryName);
        model.addAttribute("editMode", false);

        return "user-pantry"; // Nombre de la plantilla Thymeleaf para la página de despensa del usuario
    }
    
    @PostMapping("/updatePantryName")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody // Indica que el método debe devolver una respuesta JSON
    public Map<String, Object> updatePantryName(@RequestParam String newName, Principal principal) {
        String username = principal.getName();
        
        Map<String, Object> response = new HashMap<>();

        try {
            // Actualizar el nombre de la despensa en la base de datos
            pantryService.updatePantryName(username, newName);
            response.put("success", true);
        } catch (Exception e) {
            // Maneja cualquier excepción aquí y establece success en false si es necesario
            response.put("success", false);
            response.put("error", "Error al actualizar el nombre de la despensa");
        }

        return response;
    }

    @PostMapping("/updateProductPantry")
    @PreAuthorize("isAuthenticated")
    @ResponseBody
    public Map<String, Object> updateProductPantry(@RequestBody List<ProductPantryDTO> productPantryDTOs, Principal principal) {
        String username = principal.getName();

        Map<String, Object> response = new HashMap<>();

        try {
            // Actualizar las cantidades en la base de datos
            productPantryService.updateProductPantryQuantity(username, productPantryDTOs);
            response.put("success", true);
        } catch (Exception e) {
            // Maneja cualquier excepción aquí y establece success en false si es necesario
            response.put("success", false);
            response.put("error", "Error al guardar cambios (Java)");
        }

        return response;
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/removeProduct", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> removeProduct(
            @RequestParam("pantryId") Long pantryId,
            @RequestParam("productId") Long productId) {
        // Lógica para eliminar el producto de la despensa del usuario
        // Esto implicará usar el servicio de ProductPantryService para eliminar el producto.
        // También puedes manejar errores y devolver respuestas adecuadas.
        
        // Ejemplo de eliminación:
        ProductPantryId id = new ProductPantryId();
        id.setPantry(pantryRepository.findById(pantryId).orElse(null));
        id.setProduct(productRepository.findById(productId).orElse(null));
        
        if (id.getPantry() != null && id.getProduct() != null) {
            productPantryService.delete(id);
            return ResponseEntity.ok("Producto eliminado correctamente.");
        } else {
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }
    }

}
