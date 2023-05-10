package cl.ciisa.despensapp2.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_pantry")
public class ProductPantry {

    @EmbeddedId
    private ProductPantryId id;

    private int quantity;
}
