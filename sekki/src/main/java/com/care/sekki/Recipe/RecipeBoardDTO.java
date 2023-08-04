package com.care.sekki.Recipe;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RecipeBoardDTO {
	private Long reNo;
	private String title;
	private String content;
	private String id;

	private String category;
	private String cuisine;
	private String times;
	private String degree;
	private Timestamp written_time;

	private String tip;
	private String mainphoto;

	private Long stepNo;
	private String stepText;
	private String stepPhotoHolder;
	
	
	public RecipeBoardDTO() {
		materials = new ArrayList<>();
	}
	
	
	private List<MaterialDTO> materials;

	public List<MaterialDTO> getMaterials() {
		return materials;
	}

	public void setMaterials(List<MaterialDTO> materials) {
		this.materials = materials;
	}
	
	public void addMaterial(MaterialDTO material) {
        if (materials == null) {
            materials = new ArrayList<>();
        }
        materials.add(material);
    }

	public Long getReNo() {
		return reNo;
	}

	public void setReNo(Long reNo) {
		this.reNo = reNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getMainphoto() {
		return mainphoto;
	}

	public void setMainphoto(String mainphoto) {
		this.mainphoto = mainphoto;
	}

	public Long getStepNo() {
		return stepNo;
	}

	public void setStepNo(Long stepNo) {
		this.stepNo = stepNo;
	}

	public String getStepText() {
		return stepText;
	}

	public void setStepText(String stepText) {
		this.stepText = stepText;
	}

	public String getStepPhotoHolder() {
		return stepPhotoHolder;
	}

	public void setStepPhotoHolder(String stepPhotoHolder) {
		this.stepPhotoHolder = stepPhotoHolder;
	}

	public Timestamp getWritten_time() {
		return written_time;
	}

	public void setWritten_time(Timestamp written_time) {
		this.written_time = written_time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



}