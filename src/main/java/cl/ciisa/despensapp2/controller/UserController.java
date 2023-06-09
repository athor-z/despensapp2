package cl.ciisa.despensapp2.controller;

import org.springframework.web.bind.annotation.RestController;

import cl.ciisa.despensapp2.model.FoodRestriction;
import cl.ciisa.despensapp2.model.User;
import cl.ciisa.despensapp2.repository.UserRepository;
import cl.ciisa.despensapp2.services.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
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
   
    
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

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
}

