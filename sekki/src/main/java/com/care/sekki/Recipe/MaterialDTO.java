package com.care.sekki.Recipe;

import java.math.BigDecimal;

public class MaterialDTO {
	private Long mate_no;
	
	private Long re_no;
	private String materialName;
    private String materialAmount;
    
    public MaterialDTO(){
    	
    }
    
    public Long getMate_no() {
		return mate_no;
	}

	public void setMate_no(Long mate_no) {
		this.mate_no = mate_no;
	}


	public Long getRe_no() {
		return re_no;
	}

	public void setRe_no(Long re_no) {
		this.re_no = re_no;
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
