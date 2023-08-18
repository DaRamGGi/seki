package com.care.sekki.customerCenter;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.care.sekki.board.BoardDTO;

@Mapper
public interface centerMapper {
	ArrayList<BoardDTO> boardForm(@Param("begin") int begin, @Param("end") int end);

	void writeAnnouncementProc(centerDTO announceDTO);

	ArrayList<centerDTO> partAnnouncement(@Param("begin") int begin, @Param("end") int end,
			@Param("search") String search);

	ArrayList<centerDTO> allAnnouncement(int begin, int end);

	int count(String search);

	centerDTO announcementContent(int num);

	void incHit(int no);

	String announceFileDownload(int num);

	void modifyAnnouncementProc(centerDTO announcement);

	void deleteAnnouncementProc(int num);

	void plusHeart(centerDTO announcement);

	void likesPerson(@Param("user_id") String user_id, @Param("announcementNum") int announcement_num);

}
