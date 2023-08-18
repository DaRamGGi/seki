package com.care.sekki.Recipe;

import java.sql.Timestamp;

public class CommentDTO {
	private Long commnet_no;
	private String coment;
	private Long re_no;
	private String id;
	private String comment_content;
	private int rating;
	private Timestamp written_time;
	public Long getCommnet_no() {
		return commnet_no;
	}
	public void setCommnet_no(Long commnet_no) {
		this.commnet_no = commnet_no;
	}
	public String getComent() {
		return coment;
	}
	public void setComent(String coment) {
		this.coment = coment;
	}
	public Long getRe_no() {
		return re_no;
	}
	public void setRe_no(Long re_no) {
		this.re_no = re_no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public Timestamp getWritten_time() {
		return written_time;
	}
	public void setWritten_time(Timestamp written_time) {
		this.written_time = written_time;
	}
	
}
