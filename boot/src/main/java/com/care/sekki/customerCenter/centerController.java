package com.care.sekki.customerCenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.care.sekki.board.BoardDTO;

import jakarta.servlet.http.HttpSession;

@Controller
public class centerController {
	@Autowired private centerService service;
	@Autowired private centerMapper mapper;
	@Autowired private HttpSession session;
	
	/*
	 * @RequestMapping("header") public String header() { return "default/header"; }
	 * 
	 * @RequestMapping("footer") public String footer() { return "default/footer"; }
	 */
	
	@RequestMapping("announcement")
	public String announcement(
			@RequestParam(value="currentPage", required = false)String cp,
			String search, Model model) {
		service.announcement(cp, search, model);
		return "customerCenter/announcement";
	}
	
	@GetMapping("writeAnnouncement")
	public String writeAnnouncement() {
		
		return "customerCenter/writeAnnouncement";
	}
	
	@PostMapping("writeAnnouncementProc")
	public String writeAnnouncementProc(Model model, MultipartHttpServletRequest multi) {
		
		String msg = service.writeAnnouncementProc(multi);
		if(msg.equals("접근이 제한되었습니다."))
			return "redirect:announcement";
		
		if(msg.equals("게시글 작성 완료"))
			return "redirect:announcement";
		
		model.addAttribute("msg", msg);
		return "redirect:announcement";
	}
	
	@RequestMapping("announcementContent")
	public String announcementContent(
			@RequestParam(value="num", required = false)String n, Model model) {
		centerDTO announcement = service.announcementContent(n);
		
		/*
		 * if(announcement == null) { return "redirect:announcement"; }
		 */
		model.addAttribute("announcement", announcement);
		return "customerCenter/announcementContent";
	}
}
