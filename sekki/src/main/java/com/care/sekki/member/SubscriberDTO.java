package com.care.sekki.member;



public class SubscriberDTO {		
	private String id;
<<<<<<< HEAD
	private String registration_Date;
	private int subscriber_Count;
	private boolean is_Subscribed;
	
	private MemberDTO member;
	
		
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



	
	public MemberDTO getMember() {
		return member;
	}
	
	public void setMember(MemberDTO member) {
		this.member = member;
	}

	
=======
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
>>>>>>> refs/remotes/origin/gyutae

	
	
}
