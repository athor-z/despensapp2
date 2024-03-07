package cl.ciisa.despensapp2.model.dto;

import cl.ciisa.despensapp2.model.MeasureUnit;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MissingIngredientDTO {

	private Long productId;
    private String name;
    private MeasureUnit measureUnit;
    private boolean isPresentInInsufficientQuantity; // Nuevo campo
    
}
