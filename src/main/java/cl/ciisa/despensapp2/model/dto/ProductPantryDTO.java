package cl.ciisa.despensapp2.model.dto;

import lombok.Data;

@Data
public class ProductPantryDTO {
    private Long pantryId;
    private Long productId;
    private String productName;
    private String measureUnit;
    private int quantity;
}

