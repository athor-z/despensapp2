package cl.ciisa.despensapp2.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.ciisa.despensapp2.model.ProductPantry;
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

    @GetMapping("/pantry")
    public String userPantry(Model model, Principal principal) {
        String username = principal.getName();
        String userEmail = userService.findEmailByUsername(username);

        
        // Obtén la información de la despensa del usuario
        List<ProductPantry> userPantryList = productPantryService.getProductsInPantryByUsername(username);
        // Obtén el nombre de la despensa del usuario
        String pantryName = pantryService.getPantryNameByUsername(username);
        
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

}
