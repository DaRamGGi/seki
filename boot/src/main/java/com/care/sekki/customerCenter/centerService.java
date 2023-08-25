package com.care.sekki.customerCenter;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.care.sekki.board.BoardDTO;
import com.care.sekki.common.PageService;
import com.care.sekki.member.MemberDTO;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class centerService {
	@Autowired private centerMapper centerMapper;
	@Autowired private HttpSession session;
	
	//공지사항 쓰기
	public String writeAnnouncementProc(MultipartHttpServletRequest multi) {
		// TODO Auto-generated method stub
		String writer = (String)session.getAttribute("id");
		if(writer == null || writer.equals("admin") == false) {
			return "접근이 제한되었습니다.";
		}
		
		centerDTO announceDTO = new centerDTO();
		announceDTO.setCategory("announcement");
		announceDTO.setWriter(writer);
		announceDTO.setTitle(multi.getParameter("title"));
		announceDTO.setContent(multi.getParameter("content"));
		
        LocalDateTime now = LocalDateTime.now();
        String formateDate = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        announceDTO.setWriteDate(formateDate);
        
        String formateTime = now.format(DateTimeFormatter.ofPattern("HH시 mm분"));
        announceDTO.setWriteTime(formateTime);
        
        announceDTO.setFiles("");
		
		if(announceDTO.getTitle() == null || announceDTO.getTitle().isEmpty()) {
			return "제목을 입력하세요.";
		}
		
		MultipartFile file = multi.getFile("upfile");
		String fileName = file.getOriginalFilename();
		if(file.getSize() != 0) {
			// 파일의 중복을 해결하기 위해 시간의 데이터를 파일이름으로 구성함.
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			Calendar cal = Calendar.getInstance();
			fileName = sdf.format(cal.getTime()) + fileName;
			announceDTO.setFiles(fileName);
			
			// 업로드 파일 저장 경로
			String fileLocation = "C:\\javas\\upload\\";
			File save = new File(fileLocation + fileName);
			
			try {
				// 서버가 저장한 업로드 파일은 임시저장경로에 있는데 개발자가 원하는 경로로 이동
				file.transferTo(save);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		centerMapper.writeAnnouncementProc(announceDTO);
		return "작성";
	}

	public void announcement(String cp, String search, Model model) {
		// TODO Auto-generated method stub
		if(search == null){
			search = "";
		}
		
		int currentPage = 1;
		try{
			currentPage = Integer.parseInt(cp);
		}catch(Exception e){
			currentPage = 1;
		}
		
		int pageBlock = 3; // 한 페이지에 보일 데이터의 수 
		int end = pageBlock * currentPage; // 테이블에서 가져올 마지막 행번호
		int begin = end - pageBlock + 1; // 테이블에서 가져올 시작 행번호

		ArrayList<centerDTO> announcements;
		if(search.isEmpty() || search.equals("")) {
			announcements = centerMapper.allAnnouncement(begin, end);
		}else {
			announcements = centerMapper.partAnnouncement(begin, end, search);
		}
		
		int totalCount = centerMapper.count(search);
		String url = "announcement?search="+search+"&currentPage=";
		String result = PageService.printPage(url, currentPage, totalCount, pageBlock);
		
		model.addAttribute("announcements", announcements);
		model.addAttribute("result", result);
		model.addAttribute("currentPage", currentPage);
		
	}

	public centerDTO announcementContent(String n) {
		// TODO Auto-generated method stub
		int num = 0;
		try{
			num = Integer.parseInt(n);
		}catch(Exception e){
			return null;
		}
		
		centerDTO announcement = centerMapper.announcementContent(num);
		if(announcement == null) {
			System.out.println("no가 널입니다.");
			return null;
		}
		
		announcement.setHits(announcement.getHits()+1);
		incHit(announcement.getNum());

		if(announcement.getFiles() != null && announcement.getFiles().isEmpty() == false) {
			String fn = announcement.getFiles();
			String[] fileName = fn.split("-", 2);
			announcement.setFiles(fileName[1]);
		}
		return announcement;
	}
	
	public void incHit(int num) {
		centerMapper.incHit(num);
	}
	
	public boolean announceDownload(String n, HttpServletResponse res) {
		int num = 0;
		
		try{
			num = Integer.parseInt(n);
		}catch(Exception e){
			return false;
		}
		
		String fileName = centerMapper.announceDownload(num);
		if(fileName == null)
			return false;
		
		String location = "/opt/tomcat/tomcat-10/webapps/upload/";
		File file = new File(location + fileName);
		
		try {
			String[] original = fileName.split("-", 2);
			res.setHeader("Content-Disposition", 
					"attachment;filename=" + URLEncoder.encode(original[1], "UTF-8"));
			FileInputStream fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, res.getOutputStream());
			fis.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
}
