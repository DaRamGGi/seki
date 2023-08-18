package com.care.sekki.customerCenter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.HtmlUtils;

import com.care.sekki.board.BoardDTO;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class centerController {
	@Autowired
	private centerService service;
	@Autowired
	private HttpSession session;

	@RequestMapping("announcement")
	public String announcement(@RequestParam(value = "currentPage", required = false) String cp, String search,
			Model model) {
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
		if (msg.equals("접근이 제한되었습니다."))
			return "redirect:announcement";

		if (msg.equals("게시글 작성 완료"))
			return "redirect:announcement";

		model.addAttribute("msg", msg);
		return "redirect:announcement";
	}

	@RequestMapping("announcementContent")
	public String announcementContent(@RequestParam(value = "num", required = false) String n, Model model) {
		centerDTO announcement = service.announcementContent(n);

		/*
		 * if(announcement == null) { return "redirect:announcement"; }
		 */
		model.addAttribute("announcement", announcement);
		return "customerCenter/announcementContent";
	}

	@RequestMapping("announceFileDownload")
	public void announceFileDownload(@RequestParam(value = "num", required = false) String n, HttpServletResponse res)
			throws IOException {

		service.announceFileDownload(n, res);

//		boolean result = service.boardDownload(n, res);
//		if(result == false)
//			return "redirect:boardForm";
//	
//		return "forward:boardContent"; 
	}

	@GetMapping("modifyAnnouncement")
	public String modifyAnnouncement(@RequestParam(value = "num", required = false) String n, Model model) {

		String id = (String) session.getAttribute("id");
		if (id == null || id.isEmpty() || id.equals("admin") == false) {
			return "redirect:announcementContent?num=" + n;
		}

		centerDTO announcement = service.modifyAnnouncement(n);
		if (announcement == null)
			return "redirect:announcement";

		if (id.equals(announcement.getWriter()) == false)
			return "redirect:announcementContent?no=" + n;

		String escapedContent = HtmlUtils.htmlEscape(announcement.getContent());
		announcement.setContent(escapedContent);

		model.addAttribute("announcement", announcement);
		return "customerCenter/modifyAnnouncement";
	}

	@RequestMapping("modifyAnnouncementProc")
	public String modifyAnnouncementProc(Model model, MultipartHttpServletRequest multi,
			@RequestParam(value = "num", required = false) String n) {
		System.out.println(n);
		String msg = service.modifyAnnouncementProc(multi, n);
		if (msg.equals("게시글 수정 완료"))
			return "redirect:announcementContent?num" + n;

		model.addAttribute("msg", msg);
		return "board/boardWrite";
	}

	@RequestMapping("deleteAnnouncementProc")
	public String deleteAnnouncementProc(@RequestParam(value = "num", required = false) String n) {
		String msg = service.deleteAnnouncementProc(n);
		if (msg.equals("로그인"))
			return "redirect:login";

		if (msg.equals("작성자만 삭제 할 수 있습니다.")) {
			System.out.println("게시글 번호 : " + n);
			return "redirect:announcementContent?num" + n;
		}

		return "redirect:announcement";
	}

	/*
	 * @ResponseBody
	 * 
	 * @PostMapping(value="clickHeart", produces = "text/plain; charset=UTF-8")
	 * public int clickHeart(@RequestBody(required = false) int announcementNum) {
	 * //System.out.println("입력한 아이디 : " + id); return
	 * service.clickHeart(announcementNum); }
	 */
	@ResponseBody
	@PostMapping(value = "clickHeart", produces = "text/plain; charset=UTF-8")
	public int clickHeart(@RequestParam("announcement_num") int announcement_num) {
		return service.clickHeart(announcement_num);
	}

}
