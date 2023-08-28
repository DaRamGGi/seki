package com.care.sekki.memberboard;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberBoardMapper {

	ArrayList<MemberBoardDTO> memberboardForm(@Param("begin")int begin, @Param("end")int end);

	int count();

	void memberboardWriteProc(MemberBoardDTO memberboard);

	MemberBoardDTO memberboardContent(int no);
	
	void incHit(int no);

	String memberboardDownload(int no);
	
	void memberboardModifyProc(MemberBoardDTO memberboard);

	void memberboardDeleteProc(int no);

	
}

