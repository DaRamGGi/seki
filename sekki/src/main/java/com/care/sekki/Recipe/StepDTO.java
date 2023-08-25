package com.care.sekki.Recipe;

public class StepDTO {
	private Long step_no;
	
	private Long re_no;
	private String step_text;
	private String step_photoinput;
	
	public Long getStep_no() {
		return step_no;
	}
	public void setStep_no(Long step_no) {
		this.step_no = step_no;
	}
	public Long getRe_no() {
		return re_no;
	}
	public void setRe_no(Long re_no) {
		this.re_no = re_no;
	}
	public String getStep_text() {
		return step_text;
	}
	public void setStep_text(String step_text) {
		this.step_text = step_text;
	}
	public String getStep_photoinput() {
		return step_photoinput;
	}
	public void setStep_photoinput(String step_photoinput) {
		this.step_photoinput = step_photoinput;
	}

}
