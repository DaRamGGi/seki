package com.care.sekki.Recipe;

public class MaterialDTO {
	private String materialName;
    private String materialAmount;
    
    public MaterialDTO(){
    	
    }
    
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getMaterialAmount() {
		return materialAmount;
	}
	public void setMaterialAmount(String materialAmount) {
		this.materialAmount = materialAmount;
	}
	
	public MaterialDTO(String materialName, String materialAmount) {
		this.materialName = materialName;
		this.materialAmount = materialAmount;
	}
}
