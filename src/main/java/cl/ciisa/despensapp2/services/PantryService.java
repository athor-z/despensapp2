package cl.ciisa.despensapp2.services;

import cl.ciisa.despensapp2.model.Pantry;
import cl.ciisa.despensapp2.model.User;
import cl.ciisa.despensapp2.repository.PantryRepository;
import cl.ciisa.despensapp2.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PantryService {

    private final PantryRepository pantryRepository;
    private final UserRepository userRepository;

    public List<Pantry> findAll() {
        return pantryRepository.findAll();
    }

    public Optional<Pantry> findById(Long id) {
        return pantryRepository.findById(id);
    }

    public Pantry save(Pantry pantry) {
        return pantryRepository.save(pantry);
    }

    public void deleteById(Long id) {
        pantryRepository.deleteById(id);
    }
    
    public String getPantryNameByUsername(String username) {
        UserDetails user = userRepository.findByUsername(username);
        if (user != null && ((User) user).getPantry() != null) {
            return ((User) user).getPantry().getName();
        } else {
            return "Despensa sin nombre"; // Valor predeterminado si no se encuentra la despensa
        }
    }

    public void updatePantryName(String username, String newName) {
    	// Obtengo el usuario
    	UserDetails user = userRepository.findByUsername(username);
    	// Obtengo la despensa del usuario
        Pantry pantry = pantryRepository.findByUser((User)user);

        if (pantry != null) {
            // Actualizo el nombre de la despensa
            pantry.setName(newName);
            pantryRepository.save(pantry);
        }
    }    
    
    public Pantry findByUsername(String username) {
    	
    	UserDetails user = userRepository.findByUsername(username);
    	Pantry foundUserPantry = ((User) user).getPantry();
		return foundUserPantry;
    	
    }
}