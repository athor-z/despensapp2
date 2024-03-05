package cl.ciisa.despensapp2.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPantryDTO {
    private Long pantryId;
    private Long productId;
    private String productName;
    private String measureUnit;
    private int quantity;
}

