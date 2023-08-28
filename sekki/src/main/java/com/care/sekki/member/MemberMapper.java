package com.care.sekki.member;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

	MemberDTO loginProc(String id);

	String regIdCheck(String id);
	
	void registerProc(MemberDTO member);

	
	ArrayList<MemberDTO> memberInfo(
			@Param("begin")int begin, @Param("end")int end, 
			@Param("select")String select, @Param("search")String search);
	
	
	int count(@Param("select")String select, @Param("search")String search);

	int updateProc(MemberDTO member); 
/*
	void delete(String id);
	*/

	String findIdByMobile(@Param("userName")String userName, 
			@Param("mobile")String mobile);
	
	String findIdByEmail(@Param("userName")String userName, 
			@Param("email")String email);

	ArrayList<SubscriberDTO> subscriberList(@Param("begin")int begin,  @Param("end") int end, 
			@Param("select")String select, @Param("search")String search);

	
	SubscriberDTO findById(String subscriberId);

	void updateSubscription(SubscriberDTO subscriber);

	int findSubscriberCountById(String memberId);

	
	

}