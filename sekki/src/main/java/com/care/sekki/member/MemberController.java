package com.care.sekki.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.care.sekki.customerCenter.centerReplyDTO;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
	@Autowired private MemberService service;
	@Autowired private HttpSession session;
	
	@Value("${cloud.aws.s3.bucket}")
    private String bucket;
	
	@RequestMapping("index")
	public String index() {
		return "member/index";
	}

	@RequestMapping("header")
	public String header() {
		return "default/header";
	}
	@RequestMapping("main")
	public String main() {
		return "default/main";
	}
	@RequestMapping("footer")
	public String footer() {
		return "default/footer";
	}

	@GetMapping("login")
	public String login() {
		return "member/login";
	}
	
	@PostMapping("loginProc")
	public String loginProc(MemberDTO member) {
		String result = service.loginProc(member);
		if(result.equals("로그인 성공")) {
			return "redirect:index";
		}
		return "member/login";
	}
	
	@GetMapping("agreeCondition")
	public String agreeCondition() {
		return "member/agreeCondition";
	}
	
	@ResponseBody
	@PostMapping(value="sendAuthenticationNumEmail", produces = "text/plain; charset=utf-8")
	public String sendAuthenticationNumEmail(@RequestBody(required = false)String sendTo) {
		return service.sendAuthenticationNumEmail(sendTo);
	}
	
	@ResponseBody
	@PostMapping(value="checkAuthenticationNumEmail", produces = "text/plain; charset=utf-8")
	public String checkAuthenticationNum(@RequestBody(required = false) String authenticationNum) {
		//System.out.println("sendAuth()");
		return service.checkAuthenticationNumEmail(authenticationNum);
	}
	
	@ResponseBody
	@PostMapping(value="sendAuthenticationNumSms", produces = "text/plain; charset=utf-8")
	public String sendAuthenticationNumSms(@RequestBody(required = false)String sendTo) {
		return service.sendAuthenticationNumSms(sendTo);
	}
	
	@ResponseBody
	@PostMapping(value="checkAuthenticationNumSms", produces = "text/plain; charset=utf-8")
	public String checkAuthenticationNumSms(@RequestBody(required = false) String authenticationNum) {
		return service.checkAuthenticationNumSms(authenticationNum);
	}
	
	@GetMapping("register")
	public String register() {
		return "member/register";
	}
	
	@ResponseBody 
	@PostMapping(value="regIdCheck", produces = "text/plain; charset=UTF-8")
	public String regIdCheck(@RequestBody(required = false) String id) {
		return service.regIdCheck(id);
	}
	
	@ResponseBody 
	@PostMapping(value="regPwCheck", produces = "text/plain; charset=UTF-8")
	public String regPwCheck(@RequestBody(required = false) String pw) {
		return service.regPwCheck(pw);
	}
	
	@ResponseBody 
	@PostMapping(value="regUserNameCheck", produces = "text/plain; charset=UTF-8")
	public String regUserNameCheck(@RequestBody(required = false) String userName) {
		return service.regUserNameCheck(userName);
	}
	
	@ResponseBody 
	@PostMapping(value="regMobileCheck", produces = "text/plain; charset=UTF-8")
	public String regMobileCheck(@RequestBody(required = false) String mobile) {
		return service.regMobileCheck(mobile);
	}
	
	@ResponseBody 
	@PostMapping(value="regEmailCheck", produces = "text/plain; charset=UTF-8")
	public String regEmailCheck(@RequestBody(required = false) String email) {
		return service.regEmailCheck(email);
	}
	
	@ResponseBody 
	@PostMapping(value="regEmailSelectCheck", produces = "text/plain; charset=UTF-8")
	public String regEmailSelectCheck(@RequestBody(required = false) String emailSelectedOption) {
		return service.regEmailSelectCheck(emailSelectedOption);
	}
	
	@PostMapping("registerProc")
	public String registerProc(MemberDTO member, MultipartFile profilePicture) {
		System.out.println("profilePicture : " + profilePicture);
		String result = service.registerProc(member, profilePicture);
		if(result.equals("회원 가입 완료")) {
			return "redirect:index";
		}
		return "member/register";
	}
	
	@RequestMapping("memberInfo")
	public String memberInfo(
			@RequestParam(value="currentPage", required = false)String cp,
			String select, String search, Model model) {
		service.memberInfo(cp, select, search, model);
		return "member/memberInfo";
	}
	
	@RequestMapping("userInfo")
	public String userInfo(String id, 
			@RequestParam(value="currentPage", required = false)String cp, 
			Model model) {
		
		if(session.getAttribute("id") == null ) {
			return "redirect:login";
		}
		MemberDTO member = service.userInfo(id);
		if(member == null) {
			return "redirect:memberInfo?currentPage="+cp;
		}
		model.addAttribute("member", member);
		return "member/userInfo";
	}
	
	@RequestMapping("logout")
	public String logout() {
		session.invalidate();
		return "forward:index";
	}
	
	/* http://localhost:8086/dbQuiz/update */
	@GetMapping("update")
	public String update() {
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "redirect:login";
		}
		return "member/update";
	}
	@PostMapping("updateProc")
	public String updateProc(MemberDTO member, String confirm) {
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "redirect:login";
		}
		member.setId(id);
		String result = service.updateProc(member, confirm);
		if(result.equals("회원 정보 수정 완료")) {
			return "forward:logout";
		}
		return "member/update";
	}
	
	/* http://localhost:8086/dbQuiz/delete */
	@GetMapping("delete")
	public String delete() {
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "redirect:login";
		}
		return "member/delete";
	}
	
	@PostMapping("deleteProc")
	public String deleteProc(String pw, String confirmPw, Model model) {
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "redirect:login";
		}
		
		String result = service.deleteProc(id, pw, confirmPw);
		model.addAttribute("msg", result);
		if(result.equals("회원 정보 삭제 완료")) {
			return "forward:logout";
		}
		return "member/delete";
	}
	
	
	
	@Autowired private KakaoService kakao;
	@GetMapping("kakaoLogin")
	public String kakaoLogin(String code) {
		System.out.println("code : " + code);
		kakao.getAccessToken(code);
		kakao.getUserInfo();
		return "redirect:index";
	}
	
	@GetMapping("kakaoLogout")
	public String kakaoLogout() {
		kakao.unLink();
		return "redirect:index";
	}
	
}
