package com.care.sekki.member;



public class SubscriberDTO {		
	private String id;
	private String date;
	private String count;
	
	private MemberDTO member;

	

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}


	public MemberDTO getMember() {
		return member;
	}
	
	public void setMember(MemberDTO member) {
		this.member = member;
	}

	
	
}
