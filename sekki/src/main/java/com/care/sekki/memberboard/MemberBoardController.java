package com.care.sekki.memberboard;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class MemberBoardController {
	@Autowired private MemberBoardService memberboardservice;
	@Autowired private HttpSession session;
	
	
	
	
	@RequestMapping("memberboardForm")
	public String memberboardForm(
			@RequestParam(value="currentPage", required = false)String cp,
			Model model) {
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "redirect:login";
		}
		System.out.println("호출되는거야?");
		memberboardservice.memberboardForm(cp, model);
		return "memberboard/memberboardForm";
	}
		
	
	
	@GetMapping("memberboardWrite")
	public String memberboardWrite() {
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "redirect:login";
		}
		return "memberboard/memberboardWrite";
	}
	
	
	
	@PostMapping("memberboardWriteProc")
	public String memberboardWriteProc(Model model, MultipartHttpServletRequest multi) {
		String msg = memberboardservice.memberboardWriteProc(multi);
		if(msg.equals("로그인"))
			return "redirect:login";
		
		if(msg.equals("게시글 작성 완료"))
			return "redirect:memberboardForm";
		
		model.addAttribute("msg", msg);
		return "memberboard/memberboardWrite";
	}
	
	
	
	@RequestMapping("memberboardContent")
	public String memberboardContent(
			@RequestParam(value="no", required = false)String n, Model model) {
		MemberBoardDTO memberboard = memberboardservice.memberboardContent(n);
		if(memberboard == null) {
			System.out.println("memberboardContent 게시글 번호 : " + n);
			return "redirect:memberboardForm";
		}
		model.addAttribute("memberboard", memberboard);
		return "memberboard/memberboardContent";
	}
	
	

	@RequestMapping("memberboardDownload")
	public void memberboardDownload(
			@RequestParam(value="no", required = false)String n, 
			HttpServletResponse res) throws IOException{
		
		memberboardservice.memberboardDownload(n, res);

	}
	
	
	
	@GetMapping("memberboardModify")
	public String memberboardModify(
			@RequestParam(value="no", required = false)String n,
			Model model) {
		
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "redirect:login";
		}
		
		MemberBoardDTO memberboard = memberboardservice.memberboardModify(n);
		if(memberboard == null)
			return "redirect:memberboardForm";
		
		if(id.equals(memberboard.getId()) == false)
			return "redirect:memberboardContent?no="+n;
	
		model.addAttribute("memberboard", memberboard);
		return "memberboard/memberboardModify";
	}
	
	
	
	@PostMapping("memberboardModifyProc")
	public String memberboardModifyProc(MemberBoardDTO memberboard) {
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "redirect:login";
		}
		
		String msg = memberboardservice.memberboardModifyProc(memberboard);
		if(msg.equals("게시글 수정 완료"))
			return "redirect:memberboardContent?no="+memberboard.getNo();
		
		return "redirect:memberboardModify?no="+memberboard.getNo();
	}

	
	
	@RequestMapping("memberboardDeleteProc")
	public String memberboardDeleteProc(@RequestParam(value="no", required = false)String n) {
		String msg = memberboardservice.memberboardDeleteProc(n);
		if(msg.equals("로그인"))
			return "redirect:login";
		
		if(msg.equals("작성자만 삭제 할 수 있습니다.")) {
			System.out.println("게시글 번호 : " + n);
			return "forward:memberboardContent";
		}
		
		return "redirect:memberboardForm";
	}
}























