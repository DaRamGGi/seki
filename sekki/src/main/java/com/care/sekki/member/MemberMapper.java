package com.care.sekki.member;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.ui.Model;

@Mapper
public interface MemberMapper {

	MemberDTO loginProc(String id);

	void registerProc(MemberDTO member);

	
	int count(@Param("select") String select, @Param("search") String search);

	int updateProc(MemberDTO member);

	void delete(String id);

	ArrayList<SubscriberDTO> subscriberList(@Param("begin")int begin,  @Param("end") int end, 
			@Param("select")String select, @Param("search")String search);
	
	

}