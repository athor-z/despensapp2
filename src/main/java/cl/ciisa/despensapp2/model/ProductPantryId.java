package cl.ciisa.despensapp2.model;

import java.io.Serializable;
import java.util.Optional;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPantryId implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
    @ManyToOne
    @JoinColumn(name = "pantry_id")
    private Pantry pantry;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    public ProductPantryId(Pantry pantry, Optional<Product> product) {
        this.pantry = pantry;
        this.product = product.orElse(null);
    }
}