package com.care.sekki.member;



public class SubscriberDTO {		
	private String id;
	private String registration_Date;
	private int subscriber_Count;
	private boolean is_Subscribed;
	
	private MemberDTO member_id;
	
		
	public String getRegistration_Date() {
		return registration_Date;
	}


	public void setRegistration_Date(String registration_Date) {
		this.registration_Date = registration_Date;
	}


	public int getSubscriber_Count() {
		return subscriber_Count;
	}


	public void setSubscriber_Count(int subscriber_Count) {
		this.subscriber_Count = subscriber_Count;
	}


	


	public boolean isIs_Subscribed() {
		return is_Subscribed;
	}


	public void setIs_Subscribed(boolean is_Subscribed) {
		this.is_Subscribed = is_Subscribed;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public MemberDTO getMember_id() {
		return member_id;
	}


	public void setMember_id(MemberDTO member_id) {
		this.member_id = member_id;
	}



	
	

	

	
	
}
