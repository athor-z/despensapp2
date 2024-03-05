package cl.ciisa.despensapp2.services;

import cl.ciisa.despensapp2.model.Pantry;
import cl.ciisa.despensapp2.model.ProductPantry;
import cl.ciisa.despensapp2.model.User;
import cl.ciisa.despensapp2.model.dto.PantryItemDTO;
import cl.ciisa.despensapp2.repository.PantryRepository;
import cl.ciisa.despensapp2.repository.ProductPantryRepository;
import cl.ciisa.despensapp2.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PantryService {

    //private final PantryRepository pantryRepository;
    private final UserRepository userRepository;
    
    @Autowired
    private PantryRepository pantryRepository;

    @Autowired
    private ProductPantryRepository productPantryRepository;
    

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
    
    
    //NUEVO 05-03-24
    //Obtener Contenido de la despensa del usuario
    public List<PantryItemDTO> getPantryContentsForUser(Long userId) {
        // Aquí asumimos que ya tienes un método para encontrar la despensa del usuario por userId
        Pantry userPantry = pantryRepository.findByUserUsername(userRepository.findById(userId).get().getUsername());//Ojo, Evaluar comportamiento

        List<ProductPantry> pantryProducts = productPantryRepository.findByPantryUserUsername(userPantry.getUser().getUsername());
        
        // Convertir ProductPantry a PantryItemDTO
        return pantryProducts.stream()
                .map(productPantry -> new PantryItemDTO(
                        productPantry.getProduct().getId(),
                        productPantry.getProduct().getName(),
                        productPantry.getQuantity(),
                        productPantry.getProduct().getMeasureUnit().name()
                ))
                .collect(Collectors.toList());
    }
    
    public List<PantryItemDTO> getPantryContentsForRecipeIngredients(Long userId, List<Long> ingredientProductIds) {
        Pantry userPantry = pantryRepository.findByUserUsername(userRepository.findById(userId).get().getUsername());
        
        List<ProductPantry> filteredPantryProducts = productPantryRepository.findByPantryIdAndProductIdIn(userPantry.getId(), ingredientProductIds);

        return filteredPantryProducts.stream()
                .map(productPantry -> new PantryItemDTO(
                        productPantry.getProduct().getId(),
                        productPantry.getProduct().getName(),
                        productPantry.getQuantity(),
                        productPantry.getProduct().getMeasureUnit().name()
                ))
                .collect(Collectors.toList());
    }
    
    /*
    //Convertir a ProductPantryDTO **Puede que no sea necesario usarlo**
    private ProductPantryDTO convertToProductPantryDTO(ProductPantry productPantry) {
        // La implementación de este método dependerá de los campos disponibles en tu ProductPantry y ProductPantryDTO
        return new ProductPantryDTO(
                productPantry.getPantry().getId(),
                productPantry.getProduct().getId(),
                productPantry.getProduct().getName(),
                productPantry.getProduct().getMeasureUnit().name(),
                productPantry.getQuantity()
        );
    }
    */
}