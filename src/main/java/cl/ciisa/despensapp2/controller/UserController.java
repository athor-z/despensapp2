package cl.ciisa.despensapp2.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.ciisa.despensapp2.model.FoodRestriction;
import cl.ciisa.despensapp2.model.User;
import cl.ciisa.despensapp2.repository.UserRepository;
import cl.ciisa.despensapp2.services.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//@RestController //Ya no será REST
@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private UserRepository userRepository;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
   
    //NUEVO 10-03-24
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "user-register";
    }
    
    @PostMapping("/register")
    public String createUser(User user, RedirectAttributes redirectAttributes) {
        userService.createUser(user);
     //  redirectAttributes.addFlashAttribute("message", "Usuario DespensApp creado con exito!");
     // Agrega un mensaje de éxito que se mostrará en la página de login
        redirectAttributes.addFlashAttribute("registrationSuccess", "Registro exitoso. Por favor, inicia sesión.");
        // Redirige a la página de login
        return "redirect:/login"; //Se cambia a redirect para pasar el FlashAttribute a la URL de Login
    }
    
    // Método para verificar la disponibilidad del nombre de usuario
    @GetMapping("/check-username")
    @ResponseBody
    public String checkUsernameAvailability(@RequestParam String username) {
    	System.out.println("DEBUG DespensApp --- Valor String Username recibido: " + username); //Para DEBUG
    	boolean available = !userService.existsByUsername(username);
        System.out.println("DEBUG DespensApp --- Valor bool Username disponible: " + available);  //Para DEBUG
        return available ? "Disponible" : "No disponible";
    }
    
    /*
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
	*/
    
    @GetMapping("/find/{id}")
    public Optional<User> findUserById(@PathVariable Long id) {
        return userService.findUserById(id);
                //.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        User user = userRepository.findById(id).get();
                //.orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        // Actualizar propiedades según los valores en el objeto updates
        updates.forEach((key, value) -> {
            switch (key) {
                case "username":
                    user.setUsername((String) value);
                    break;
                case "email":
                    user.setEmail((String) value);
                    break;
                case "password":
                    user.setPassword((String) value);
                    break;
                case "restriction":
                /*    user.setRestriction((FoodRestriction) value);
                    break;
                */
                    if (value instanceof String) {
                        try {
                            user.setRestriction(FoodRestriction.valueOf((String) value));
                        } catch (IllegalArgumentException e) {
                            // Ignorar valores de restricción desconocidos
                        }
                    }
                    break;
                default:
                    // Ignorar propiedades desconocidas
                    break;
            }
        });

        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }
    
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/restriccion-alimentaria/{restriction}")
    public List<User> findUsersByRestriction(@PathVariable FoodRestriction restriction) {
        return userService.findUsersByRestriction(restriction);
    }
    
    
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Agrega los datos que necesitas mostrar en la vista
        model.addAttribute("message", "Hello, User! Welcome to your dashboard.");
        // Devuelve el nombre de la vista
        return "dashboard";
    }
/*    
    //Prestemosle ojo a esta funcion
    @GetMapping("/profile")
    public String userProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Obtiene el nombre de usuario del usuario autenticado
        // Realiza acciones basadas en el usuario autenticado
        return "profile"; // Reemplaza con el nombre de tu vista Thymeleaf para el perfil de usuario
    }
*/
    

    
}

