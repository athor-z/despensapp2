package cl.ciisa.despensapp2.model.dto;

public class PantryItemDTO {

    private Long productId;
    private String ingredientName;
    private double quantity;
    private String measureUnit;
    private boolean sufficientQuantity;

    public PantryItemDTO(Long productId, String ingredientName, double quantity, String measureUnit, boolean sufficientQuantity) {
        this.productId = productId;
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.measureUnit = measureUnit;
        this.setSufficientQuantity(sufficientQuantity);
    }

    // Getters y Setters

    public PantryItemDTO(Long productId, String ingredientName, double quantity, String measureUnit) {
        this.productId = productId;
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.measureUnit = measureUnit;
	}

	public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

	public boolean isSufficientQuantity() {
		return sufficientQuantity;
	}

	public void setSufficientQuantity(boolean sufficientQuantity) {
		this.sufficientQuantity = sufficientQuantity;
	}
    
    // Método toString() opcional para facilitar la depuración
    @Override
    public String toString() {
        return "PantryItemDTO{" +
                "productId=" + productId +
                ", ingredientName='" + ingredientName + '\'' +
                ", quantity=" + quantity +
                ", measureUnit='" + measureUnit + '\'' +
                ", sufficientQuantity='" + sufficientQuantity + '\''+
                '}';
    }


}
