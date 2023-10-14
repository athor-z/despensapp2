package cl.ciisa.despensapp2.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ciisa.despensapp2.model.ProductPantry;
import cl.ciisa.despensapp2.model.ProductPantryId;
import cl.ciisa.despensapp2.model.dto.ProductPantryDTO;
import cl.ciisa.despensapp2.repository.PantryRepository;
import cl.ciisa.despensapp2.repository.ProductPantryRepository;
import cl.ciisa.despensapp2.repository.ProductRepository;

@Service
public class ProductPantryService {
	
    @Autowired
    private ProductPantryRepository productPantryRepository;
    @Autowired
    private PantryRepository pantryRepository;
    @Autowired
    private ProductRepository productRepository;

    public ProductPantry save(ProductPantry productPantry) {
        return productPantryRepository.save(productPantry);
    }

    public void delete(ProductPantryId id) {
        productPantryRepository.deleteById(id);
    }

    public List<ProductPantry> getAll() {
        return productPantryRepository.findAll();
    }

    public ProductPantry getById(ProductPantryId id) {
        return productPantryRepository.findById(id).orElse(null);
    }
    //NUEVO
    public long countProductsInPantryByUsername(String username) {
        return productPantryRepository.countByPantryUserUsername(username);
    }
    //Otro NUEVO
    public List<ProductPantry> getProductsInPantryByUsername(String username) {
        return productPantryRepository.findByPantryUserUsername(username);
    }
    
    public List<ProductPantryDTO> getProductPantryDTOsByUsername(String username) {
        List<ProductPantry> productPantries = productPantryRepository.findByPantryUserUsername(username);
        List<ProductPantryDTO> productPantryDTOs = new ArrayList<>();

        for (ProductPantry productPantry : productPantries) {
            ProductPantryDTO productPantryDTO = convertToDTO(productPantry);
            productPantryDTOs.add(productPantryDTO);
        }

        return productPantryDTOs;
    }
    private ProductPantryDTO convertToDTO(ProductPantry productPantry) {
        ProductPantryDTO productPantryDTO = new ProductPantryDTO();
        productPantryDTO.setPantryId(productPantry.getId().getPantry().getId());
        productPantryDTO.setProductId(productPantry.getId().getProduct().getId());
        productPantryDTO.setProductName(productPantry.getId().getProduct().getName());
        productPantryDTO.setMeasureUnit(productPantry.getId().getProduct().getMeasureUnit().toString());
        productPantryDTO.setQuantity(productPantry.getQuantity());
        // Establece otros campos DTO según sea necesario

        return productPantryDTO;
    }

    public void updateProductPantryQuantity(String username, List<ProductPantryDTO> productPantryDTOs) {
        for (ProductPantryDTO dto : productPantryDTOs) {
            ProductPantryId productPantryId = new ProductPantryId();
            productPantryId.setPantry(pantryRepository.findByUserUsername(username));
            productPantryId.setProduct(productRepository.findById(dto.getProductId()).orElse(null));

            ProductPantry productPantry = productPantryRepository.findById(productPantryId).orElse(null);

            if (productPantry != null) {
                productPantry.setQuantity(dto.getQuantity());
                productPantryRepository.save(productPantry);
            }
        }
    }
    //public long getCountOfProductsInPantryByUsername(String username) {
    //    return productPantryRepository.countByUserUsername(username);
    //}
}