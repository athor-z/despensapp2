package cl.ciisa.despensapp2.model.dto;

import cl.ciisa.despensapp2.model.MeasureUnit;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientProductDTO {
    private String productName;
    private int quantity;
    private MeasureUnit measureUnit;
}
