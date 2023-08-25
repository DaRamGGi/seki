package com.care.sekki.Recipe;


import java.sql.Timestamp;

import java.util.List;

public class RecipeBoardDTO {
	private Long re_no;
	private String title;
	private String content;
	private String id;

	private String category;
	private String cuisine;
	private String times;
	private String degree;
	private Timestamp written_time;

	private String tip;
	private String mainphoto = "";

	
	private List<MaterialDTO> mararials;
	private List<StepDTO> steps;

	public RecipeBoardDTO() {
		this.mainphoto = "";
	}
	

	
	public List<MaterialDTO> getMararials() {
		return mararials;
	}

	public void setMararials(List<MaterialDTO> mararials) {
		this.mararials = mararials;
	}

	public List<StepDTO> getSteps() {
		return steps;
	}

	public void setSteps(List<StepDTO> steps) {
		this.steps = steps;
	}



	public Long getRe_no() {
		return re_no;
	}



	public void setRe_no(Long re_no) {
		this.re_no = re_no;
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