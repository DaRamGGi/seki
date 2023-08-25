package com.care.customerCenter;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.care.sekki.board.BoardDTO;
import com.care.sekki.common.PageService;
import com.care.sekki.member.MemberDTO;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class centerService {
	@Autowired
	private centerMapper centerMapper;
	@Autowired
	private HttpSession session;

	// 공지사항 쓰기
	public String writeAnnouncementProc(MultipartHttpServletRequest multi) {
		// TODO Auto-generated method stub
		String writer = (String) session.getAttribute("id");
		if (writer == null || writer.equals("admin") == false) {
			return "접근이 제한되었습니다.";
		}

		centerDTO announcement = new centerDTO();
		announcement.setCategory("announcement");
		announcement.setWriter("관리자");
		announcement.setTitle(multi.getParameter("title"));
		announcement.setContent(multi.getParameter("content").replaceAll("\n", "<br>"));

		LocalDateTime now = LocalDateTime.now();
		String formateDate = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
		announcement.setWriteDate(formateDate);

		String formateTime = now.format(DateTimeFormatter.ofPattern("HH시 mm분"));
		announcement.setWriteTime(formateTime);

		announcement.setFiles("");

		if (announcement.getTitle() == null || announcement.getTitle().isEmpty()) {
			return "제목을 입력하세요.";
		}

		MultipartFile file = multi.getFile("upfile");
		String fileName = file.getOriginalFilename();
		if (file.getSize() != 0) {
			// 파일의 중복을 해결하기 위해 시간의 데이터를 파일이름으로 구성함.
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			Calendar cal = Calendar.getInstance();
			fileName = sdf.format(cal.getTime()) + fileName;
			announcement.setFiles(fileName);

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

		centerMapper.writeAnnouncementProc(announcement);
		return "작성";
	}

	public void announcement(String cp, String search, Model model) {
		// TODO Auto-generated method stub
		if (search == null) {
			search = "";
		}

		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(cp);
		} catch (Exception e) {
			currentPage = 1;
		}

		int pageBlock = 3; // 한 페이지에 보일 데이터의 수
		int end = pageBlock * currentPage; // 테이블에서 가져올 마지막 행번호
		int begin = end - pageBlock + 1; // 테이블에서 가져올 시작 행번호

		ArrayList<centerDTO> announcements;
		if (search.isEmpty() || search.equals("")) {
			announcements = centerMapper.allAnnouncement(begin, end);
		} else {
			announcements = centerMapper.partAnnouncement(begin, end, search);
		}

		int totalCount = centerMapper.count(search);
		String url = "announcement?search=" + search + "&currentPage=";
		String result = PageService.printPage(url, currentPage, totalCount, pageBlock);

		model.addAttribute("announcements", announcements);
		model.addAttribute("result", result);
		model.addAttribute("currentPage", currentPage);

	}

	public centerDTO announcementContent(String n) {
		// TODO Auto-generated method stub
		int num = 0;
		try {
			num = Integer.parseInt(n);
		} catch (Exception e) {
			return null;
		}

		centerDTO announcement = centerMapper.announcementContent(num);
		if (announcement == null) {
			System.out.println("no가 널입니다.");
			return null;
		}

		announcement.setHits(announcement.getHits() + 1);
		incHit(announcement.getNum());

		if (announcement.getFiles() != null && announcement.getFiles().isEmpty() == false) {
			String fn = announcement.getFiles();
			String[] fileName = fn.split("-", 2);
			announcement.setFiles(fileName[1]);
		}
		return announcement;
	}

	public void incHit(int num) {
		centerMapper.incHit(num);
	}

	public boolean announceFileDownload(String n, HttpServletResponse res) {
		// TODO Auto-generated method stub
		int num = 0;

		try {
			num = Integer.parseInt(n);
		} catch (Exception e) {
			return false;
		}

		String fileName = centerMapper.announceFileDownload(num);
		if (fileName == null)
			return false;

		String location = "/opt/tomcat/tomcat-10/webapps/upload/";
		File file = new File(location + fileName);

		try {
			String[] original = fileName.split("-", 2);
			res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(original[1], "UTF-8"));
			FileInputStream fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, res.getOutputStream());
			fis.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	public centerDTO modifyAnnouncement(String n) {
		int num = 0;
		try {
			num = Integer.parseInt(n);
		} catch (Exception e) {
			return null;
		}

		centerDTO announcement = centerMapper.announcementContent(num);
		if (announcement == null)
			return null;

		announcement.setContent(announcement.getContent().replaceAll("<br>", "\n"));

		if (announcement.getFiles() != null && announcement.getFiles().isEmpty() == false) {
			String fn = announcement.getFiles();
			String[] fileName = fn.split("-", 2);
			announcement.setFiles(fileName[1]);
		}
		return announcement;
	}

	public String modifyAnnouncementProc(MultipartHttpServletRequest multi, String n) {
		// TODO Auto-generated method stub
		int num = 0;
		try {
			num = Integer.parseInt(n);
		} catch (Exception e) {
			return null;
		}

		centerDTO announcement = centerMapper.announcementContent(num);
		if (announcement == null) {
			return null;
		}

		announcement.setTitle(multi.getParameter("title"));
		announcement.setContent(multi.getParameter("content").replaceAll("\n", "<br>"));

		LocalDateTime now = LocalDateTime.now();
		String formateDate = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
		announcement.setWriteDate(formateDate);

		String formateTime = now.format(DateTimeFormatter.ofPattern("HH시 mm분"));
		announcement.setWriteTime(formateTime);

		/* announcement.setFiles(announcement.getFiles()); */
		announcement.setFiles("");

		MultipartFile file = multi.getFile("upfile");
		String fileName = file.getOriginalFilename();
		if (file.getSize() != 0) {
			/*
			 * 기존 파일 삭제 String path = "C:\\javas\\upload\\" +announcement.getFiles(); File
			 * originFile = new File(path); if(originFile.exists() == true) {
			 * originFile.delete(); }
			 */
			// 파일의 중복을 해결하기 위해 시간의 데이터를 파일이름으로 구성함.
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			Calendar cal = Calendar.getInstance();
			fileName = sdf.format(cal.getTime()) + fileName;
			announcement.setFiles(fileName);

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

		centerMapper.modifyAnnouncementProc(announcement);
		return "게시글 수정 완료";
	}

	public String deleteAnnouncementProc(String n) {
		// TODO Auto-generated method stub
		System.out.println(n);
		String id = (String) session.getAttribute("id");
		if (id == null || id.isEmpty() || id.equals("admin") == false) {
			return "로그인";
		}

		int num = 0;
		try {
			num = Integer.parseInt(n);
		} catch (Exception e) {
			return "게시글 번호에 문제가 생겼습니다.";
		}

		centerDTO announcement = centerMapper.announcementContent(num);
		if (announcement == null)
			return "게시글 번호에 문제가 생겼습니다.";

		centerMapper.deleteAnnouncementProc(num);

		String path = "C:\\javas\\upload\\" + announcement.getFiles();
		File file = new File(path);
		if (file.exists() == true) {
			file.delete();
		}

		return "게시글 삭제 완료";
	}

	public int clickHeart(int announcement_num) {
		// 하트 수 올리기(게시판 테이블)
		centerDTO announcement = centerMapper.announcementContent(announcement_num);
		System.out.println(announcement.getLikes());
		announcement.setLikes(announcement.getLikes() + 1);
		centerMapper.plusHeart(announcement);

		// 좋아요 누른 사람 저장(하트 테이블)
		String user_id = (String) session.getAttribute("id");
		centerMapper.likesPerson(user_id, announcement_num);

		return announcement.getLikes();
	}

}
