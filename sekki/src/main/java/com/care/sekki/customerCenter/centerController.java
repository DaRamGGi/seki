package com.care.sekki.customerCenter;

import java.io.IOException;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;

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

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.nurigo.java_sdk.exceptions.CoolsmsException;


@Controller
public class centerController {
	@Autowired private centerService service;
	@Autowired private HttpSession session;
	//공통
	@RequestMapping("fileDownload")
	public void fileDownload(
			@RequestParam(value="num", required = false)String n, 
			HttpServletResponse res) throws IOException{
		
		service.fileDownload(n, res);
		
	}
	
	//공지사항 목록
	@RequestMapping("announcement")
	public String announcement(
			@RequestParam(value="currentPage", required = false)String cp,
			String search, Model model) {
		
		service.announcement(cp, search, model);
		
		return "customerCenter/announcement";
	}
	
	//공지사항 등록
	@GetMapping("writeAnnouncement")
	public String writeAnnouncement() {
		return "customerCenter/writeAnnouncement";
	}
	
	@PostMapping("writeAnnouncementProc")
	public String writeAnnouncementProc(Model model, MultipartHttpServletRequest multi) {
		service.writeAnnouncementProc(multi);
		return "redirect:announcement";
	}
	
	//공지사항 내용
	@RequestMapping("announcementContent")
	public String announcementContent(
			@RequestParam(value="num", required = false)String n, Model model) {
		centerDTO announcement = service.centerContent(n);
		
		if(announcement == null) 
			return "redirect:announcement"; 
		 
		model.addAttribute("announcement", announcement);
		
		//좋아요
		String user_id = (String)session.getAttribute("id");
	    if (user_id != null) {
	        String category = "announcement";
	        int checkLikeStatus = service.checkLikeStatus(user_id, announcement.getNum(), category);
	        model.addAttribute("isLiked", checkLikeStatus);
	    }
		
	    //댓글 
	    int num = 0;
		try{
			num = Integer.parseInt(n);
		}catch(Exception e){
			return null;
		}
		
	    ArrayList<centerReplyDTO> announcementReplys = service.announcementReply(num);
		
	    model.addAttribute("announcementReplys", announcementReplys);
		return "customerCenter/announcementContent";
	}
	
	@GetMapping("modifyAnnouncement")
	public String modifyAnnouncement(
		@RequestParam(value="num", required = false)String n,
		Model model) {

		centerDTO announcement = service.modifyAnnouncement(n);
		if(announcement == null)
			return "redirect:announcement";

		//textarea에 <br>태그가 나오지 않게
		//객체의 내용에 대해 HTML 특수 문자를 이스케이프(escape) 처리
		String escapedContent = HtmlUtils.htmlEscape(announcement.getContent());
		announcement.setContent(escapedContent);
		
		model.addAttribute("announcement", announcement);
		return "customerCenter/modifyAnnouncement";
	}
	
	@RequestMapping("modifyAnnouncementProc")
	public String modifyAnnouncementProc(MultipartHttpServletRequest multi,
			@RequestParam(value="num", required = false)String n) {
		
		String msg = service.modifyAnnouncementProc(multi, n);
		
		if(msg.equals("게시글 수정 완료"))
			return "redirect:announcementContent?num="+n;
		
		return "customerCenter/announcement";
	}
	
	@RequestMapping("deleteAnnouncementProc")
	public String deleteAnnouncementProc(@RequestParam(value="num", required = false)String n) {
		service.deleteCenterProc(n);
		return "redirect:announcement";
	}
	
	@ResponseBody
	@PostMapping(value = "clickHeart", produces = "text/plain;charset=UTF-8")
	public String clickHeart(@RequestParam(value = "num") int center_num) {
	    String user_id = (String) session.getAttribute("id");
	    if (user_id != null) {
	        String category = "announcement";
	        int checkLikeStatus = service.checkLikeStatus(user_id, center_num, category);
	        
	        if (checkLikeStatus == 1) {// 좋아요 취소
	            service.likesPersonDelete(user_id, center_num, category);
	            return "0"; 
	        } else {// 좋아요
	            service.likesPersonSave(user_id, center_num, category);
	            return "1"; 
	        }
	    }
	    return "2"; // 사용자가 로그인하지 않은 경우
	}
	
	@ResponseBody
	@PostMapping(value="myReplyOnly", produces = "application/json; charset=UTF-8")
	public List<centerReplyDTO> myReplyOnly(@RequestParam(value = "num") int center_num, 
			@RequestBody(required = false) String checkStatus) {
		
		if(checkStatus.equals("checked"))
			return service.myReplyOnly(center_num);
		
		return service.announcementReply(center_num);
	}
	
	@ResponseBody
	@RequestMapping(value="writeAnnouncementReply", produces = "application/json; charset=UTF-8")
	public List<centerReplyDTO> writeAnnouncementReply(@RequestParam(value = "num") int center_num, 
			@RequestBody(required = false) String reply) {
		
		String user_id = (String)session.getAttribute("id");
	    if (user_id == null) {
	        return null;
	    }
	    
		return service.writeAnnouncementReply(center_num, reply);
	}
	
	@ResponseBody
	@PostMapping(value="deleteReply", produces = "application/json; charset=UTF-8")
	public List<centerReplyDTO> deleteReply(@RequestParam(value = "replyNum") int reply_num, 
			@RequestParam(value = "num") int center_num) {
		
		return service.deleteReply(reply_num, center_num);
	}
	
	@ResponseBody
	@PostMapping(value="announcementReplyModifyClick", produces = "application/json; charset=UTF-8")
	public List<centerReplyDTO> announcementReplyModifyClick(@RequestParam(value = "replyNum") int reply_num ,
			@RequestParam(value = "centerNum") int center_num) {
		return service.announcementReplyModifyClick(reply_num, center_num);
	}
	
	@ResponseBody
	@PostMapping(value="announcementReplyModify", produces = "application/json; charset=UTF-8")
	public centerReplyDTO announcementReplyModify(@RequestParam(value = "replyNum") int reply_num ,
			@RequestParam(value = "centerNum") int center_num, @RequestBody(required = false) String reply) {
		return service.announcementReplyModify(reply_num, center_num, reply);
	}
	
	//문의하기-------------------------------------------------------------------------------------
	@RequestMapping("inquiry")
	public String inquiry(
			@RequestParam(value="currentPage", required = false)String cp,
			String search, Model model) {
		service.inquiry(cp, search, model);
		return "customerCenter/inquiry";
	}
	
	@ResponseBody
	@PostMapping(value="writeInquiryLoginCheck", produces = "text/plain;charset=UTF-8")
	public String writeInquiryLoginCheck() {
		String usre_id = (String)session.getAttribute("id");
		if(usre_id != null)
			return "1";
		
		return "0";
	}
	
	@GetMapping("writeInquiry")
	public String writeInquiry() {
		
		return "customerCenter/writeInquiry";
	}
	
	@PostMapping("writeInquiryProc")
	public String writeInquiryProc(Model model, MultipartHttpServletRequest multi) {
		
		service.writeInquiryProc(multi);
		
		return "redirect:inquiry";
	}
	
	@ResponseBody
	@PostMapping(value="inquirySecretCheck", produces = "text/plain;charset=UTF-8")
	public String inquirySecretCheck(@RequestParam(value = "num") int num) {
	    return service.inquirySecretCheck(num);
	}
	
	@RequestMapping("SecretPwInput")
	public String SecretPwInput() {
		return "customerCenter/SecretPwInput";
	}
	
	@ResponseBody
	@PostMapping(value="secretPwCheck", produces = "text/plain;charset=UTF-8")
	public String secretPwCheck(@RequestParam(value = "num") int num,
			@RequestBody(required = false) String secretPw) {
	    return service.secretPwCheck(num, secretPw);
	}
	
	@RequestMapping("inquiryContent")
	public String inquiryContent(
			@RequestParam(value="num", required = false)String n, Model model) {
		centerDTO inquiry = service.centerContent(n);
		
		if(inquiry == null) 
			return "redirect:inquiry"; 
		 
		model.addAttribute("inquiry", inquiry);
		
		//답변
		ArrayList<centerReplyDTO> inquiryReplys = service.inquiryReplys(n);
		model.addAttribute("inquiryReplys", inquiryReplys);
		
		return "customerCenter/InquiryContent";
	}
	
	@GetMapping("adminReply")
	public String adminReply(
			@RequestParam(value="num", required = false)String n, Model model) {
		centerDTO inquiry = service.centerContent(n);
		if(inquiry == null) 
			return "redirect:inquiry"; 
		
		model.addAttribute("inquiry", inquiry);
		
		return "customerCenter/adminReply";
	}
	
	@RequestMapping("adminReplyProc")
	public String adminReplyProc(
			@RequestParam(value="num", required = false)String n, 
			@RequestParam(name = "replyContent") String replyContent,
			Model model) {
		String phoneNumber = service.adminReplyProc(n, replyContent);
		if(phoneNumber != null) {
			//service.sendAdminReply(phoneNumber);
			return "redirect:inquiryContent?num="+n;
		}
		
		return "redirect:inquiryContent?num="+n;
	}
	
	@ResponseBody
	@PostMapping(value="modifyAble", produces = "text/plain;charset=UTF-8")
	public String modifyAble(@RequestParam(value = "centerNum") int centerNum) {
		String modifyAble = service.modifyAble(centerNum);
		if(modifyAble.equals("수정 가능"))
			return "1";
 	    return "0";
	}
	
	@GetMapping("modifyInquiry")
	public String modifyInquiry(
		@RequestParam(value="num", required = false)String n,
		Model model) {

		centerDTO inquiry = service.modifyInquiry(n);
		if(inquiry == null)
			return "redirect:inquiry";

		/*
		  textarea에 <br>태그가 나오지 않게 객체의 내용에 대해 HTML 특수 문자를 이스케이프(escape) 처리 -> 서비스에서 replaceAll 메서드로 대체
		  String escapedContent = HtmlUtils.htmlEscape(inquiry.getContent());
		  inquiry.setContent(escapedContent);
		*/
		
		model.addAttribute("inquiry", inquiry);
		return "customerCenter/modifyInquiry";
	}
	
	@RequestMapping("modifyInquiryProc")
	public String modifyInquiryProc(MultipartHttpServletRequest multi,
			@RequestParam(value="num", required = false)String n) {
		
		String msg = service.modifyInquiryProc(multi, n);
		
		if(msg.equals("게시글 수정 완료"))
			return "redirect:inquiryContent?num="+n;
		
		return "customerCenter/inquiry";
	}
	
	@RequestMapping("deleteInquiryProc")
	public String deleteInquiryProc(@RequestParam(value="num", required = false)String n) {
		service.deleteCenterProc(n);
		return "redirect:inquiry";
	}
	
	@ResponseBody
	@RequestMapping(value="modifyAdminReplyClick", produces = "application/json; charset=UTF-8")
	public List<centerReplyDTO> modifyAdminReplyClick(@RequestParam(value = "replyNum") int reply_num,
			@RequestParam(value = "centerNum")int center_num) {
		
		return service.modifyAdminReplyClick(reply_num, center_num);
	}
	
	@ResponseBody
	@RequestMapping(value="modifyAdminReply", produces = "application/json; charset=UTF-8")
	public centerReplyDTO modifyAdminReply(@RequestParam(value = "replyNum") int reply_num,
			@RequestParam(value = "centerNum")int center_num, @RequestBody(required = false) String modifiedContent) {
		return service.modifyAdminReply(reply_num, center_num, modifiedContent);
	}
	
	@RequestMapping("deleteAdminReply")
	public String deleteAdminReply(@RequestParam(value="replyNum", required = false)int replyNum,
			@RequestParam(value="centerNum", required = false)int centerNum) {
		service.deleteAdminReply(replyNum , centerNum);
		return "redirect:inquiryContent?num="+centerNum;
	}
	
=======

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

>>>>>>> refs/remotes/origin/gyutae
}
