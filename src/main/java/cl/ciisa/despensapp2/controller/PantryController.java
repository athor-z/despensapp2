package cl.ciisa.despensapp2.controller;

import cl.ciisa.despensapp2.model.Pantry;
import cl.ciisa.despensapp2.model.User;
import cl.ciisa.despensapp2.repository.UserRepository;
import cl.ciisa.despensapp2.services.PantryService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/pantries")
public class PantryController {

	private final PantryService pantryService;
	private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Pantry>> getAllPantries() {
        List<Pantry> pantryList = pantryService.findAll();
        return new ResponseEntity<>(pantryList, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Pantry> getPantryById(@PathVariable Long id) {
        Pantry pantry = pantryService.findById(id).get();
        return new ResponseEntity<>(pantry, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Pantry> addPantry(@RequestBody Pantry pantry) {
        User user = pantry.getUser();
        if (user != null && user.getId() != null) {
            user = userRepository.findById(user.getId()).orElse(null);
        }
        pantry.setUser(user);
        Pantry newPantry = pantryService.save(pantry);
        return new ResponseEntity<>(newPantry, HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Pantry> updatePantry(@PathVariable Long id, @RequestBody Pantry pantry) {
        Pantry updatedPantry = pantryService.save(pantryService.findById(id).get());
        return new ResponseEntity<>(updatedPantry, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePantry(@PathVariable Long id) {
        pantryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
 
}
