package com.care.sekki.member;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.care.sekki.S3.S3UploadService;
import com.care.sekki.common.PageService;

import jakarta.servlet.http.HttpSession;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Service
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private HttpSession session;
	@Autowired
	private S3UploadService s3UploadService;

	/* 로그인======================================================= */
	public String loginProc(MemberDTO member) {
		if (member.getId() == null || member.getId().isEmpty()) {
			return "아이디를 입력하세요.";
		}

		if (member.getPw() == null || member.getPw().isEmpty()) {
			return "비밀번호를 입력하세요.";
		}

		MemberDTO result = memberMapper.loginProc(member.getId());
		if (result != null) {
			BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();

			if (bpe.matches(member.getPw(), result.getPw())) {
				session.setAttribute("id", result.getId());
				session.setAttribute("userName", result.getUserName());
				session.setAttribute("address", result.getAddress());
				session.setAttribute("mobile", result.getMobile());
				session.setAttribute("profilePictureUrl", result.getProfilePictureUrl());
				session.setAttribute("email", result.getEmail());
				// session.setAttribute("height", result.getHeight());
				// session.setAttribute("weight", result.getWeight());
				return "로그인 성공";
			}
		}

		return "아이디/비밀번호를 확인 후 다시 시도하세요.";
	}

	/* 약관동의&본인인증======================================================= */

	private String auth;
	@Autowired
	private MailService mailService;

	public String sendAuthenticationNumEmail(String sendTo) {
		if (sendTo == null || sendTo.isEmpty())
			return "* 이메일 주소를 입력하세요.";

		Random r = new Random();
		auth = String.format("%06d", r.nextInt(1000000));

		String msg = mailService.sendAuthenticationNumEmail(sendTo, "(자취세끼) 회원가입 본인인증",
				"(자취세끼) 가입을 환영합니다. 인증번호 6자리 [" + auth + "] 를 입력해주세요.");
		if (msg.equals("* 입력하신 이메일 주소로 인증번호를 전송했습니다.") == false) {
			auth = "";
		}

		return msg;
	}

	public String checkAuthenticationNumEmail(String authenticationNum) {
		if (auth == null || auth.isEmpty())
			return "* 이메일로 인증번호가 전송되지 않았습니다.";

		if (authenticationNum == null || authenticationNum.isEmpty())
			return "* 인증번호를 입력하세요.";

		if (authenticationNum.equals(auth)) {
			return "* 인증 성공. 다음 단계로 이동하세요.";
		}

		return "* 인증번호가 틀립니다.";
	}

	@Autowired
	private SmsService smsService;
	String result;

	public String sendAuthenticationNumSms(String sendTo) {
		if (sendTo == null || sendTo.isEmpty())
			return "* 휴대폰 번호를 입력하세요.";

		Random r = new Random();
		auth = String.format("%06d", r.nextInt(1000000));

		result = smsService.sendAuthenticationNumSms(sendTo, auth);
		return result;
	}

	public String checkAuthenticationNumSms(String authenticationNum) {
		if (result.equals("* 인증번호 전송을 실패했습니다."))
			return "* 휴대폰으로 인증번호가 전송되지 않았습니다.";

		if (authenticationNum == null || authenticationNum.isEmpty())
			return "* 인증번호를 입력하세요.";

		if (authenticationNum.equals(auth)) {
			return "* 인증 성공. 다음 단계로 이동하세요.";
		}

		return "* 인증번호가 틀립니다.";
	}

	/* 회원가입======================================================= */
	public String regIdCheck(String id) {
		if (id == null || id.isEmpty())
			return "* 아이디를 입력하세요.";

		Pattern pattern = Pattern.compile("^[a-z0-9]{1}[a-z0-9_-]{4,19}$");
		Matcher matcher = pattern.matcher(id);
		if (matcher.matches() == false)
			return "* 5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.";

		String user_id = memberMapper.regIdCheck(id);
		if (user_id == null)
			return "";

		return "* 중복되는 아이디 입니다. 다른 아이디를 입력하세요.";
	}

	public String regPwCheck(String pw) {
		if (pw == null || pw.isEmpty())
			return "* 비빌번호를 입력하세요.";

		Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$");
		Matcher matcher = pattern.matcher(pw);
		if (matcher.matches() == false)
			return "* 영문, 숫자, 특수문자 세 가지를 모두 포함 (8~15자)";

		return "";
	}

	public String regConfirmCheck(String pw) {
		if (pw == null || pw.isEmpty())
			return "* 비빌번호를 입력하세요.";

		Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$");
		Matcher matcher = pattern.matcher(pw);
		if (matcher.matches() == false)
			return "* 영문, 숫자, 특수문자 세 가지를 모두 포함 (8~15자)";

		return "";
	}

	public String regUserNameCheck(String userName) {
		if (userName == null || userName.isEmpty())
			return "* 이름을 입력하세요.";

		if (userName.length() < 2) {
			return "* 이름은 두 글자 이상 입력하세요.";
		}

		Pattern pattern = Pattern.compile("^[가-힣a-zA-Z]*$");
		Matcher matcher = pattern.matcher(userName);
		if (matcher.matches() == false)
			return "* 이름은 숫자와 특수문자를 포함할 수 없습니다.";

		return "";
	}

	public String regMobileCheck(String mobile) {
		if (mobile == null || mobile.isEmpty())
			return "* 전화번호를 입력하세요.";
		
		Pattern pattern = Pattern.compile("^(01[016789])([1-9]\\d{2,3})(\\d{4})$");
		Matcher matcher = pattern.matcher(mobile);
		if (matcher.matches() == false)
			return "* 유효하지 않은 번호입니다. (-)없이 입력하세요.";
		
		return "";
	}

	public String regEmailCheck(String email) {
		if (email == null || email.isEmpty())
			return "* 이메일을 입력하세요.";

		return "* 메일주소를 선택하세요";
	}

	public String regEmailSelectCheck(String emailSelectedOption) {
		if (emailSelectedOption == null || emailSelectedOption.isEmpty())
			return "* 메일주소를 선택하세요";

		if (emailSelectedOption.equals("@ 선택"))
			return "* 메일주소를 선택하세요";

		return "";
	}

	public String registerProc(MemberDTO member, MultipartFile profilePicture) {
		String memberId = member.getId();

		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
		String cryptPassword = bpe.encode(member.getPw());
		member.setPw(cryptPassword);
		
		member.setEmail(member.getEmail()+member.getEmailSelect());
		try {
			// 프로필 사진을 S3에 업로드하고 해당 URL을 받아옵니다.
			String profilePictureUrl = s3UploadService.saveFile(profilePicture, memberId);
			member.setProfilePictureUrl(profilePictureUrl); // 회원 정보에 프로필 사진 URL을 설정합니다.

			memberMapper.registerProc(member);
			return "회원 가입 완료";
		} catch (IOException e) {
			e.printStackTrace();
			return "회원 등록 실패: 프로필 사진 업로드 중 오류가 발생하였습니다.";
		}
	}
	
	/*
	public void memberInfo(String cp, String select, String search, Model model) {
		if (select == null) {
			select = "";
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

		ArrayList<MemberDTO> members = memberMapper.memberInfo(begin, end, select, search);
		int totalCount = memberMapper.count(select, search);
		String url = "memberInfo?select=" + select + "&search=" + search + "&currentPage=";
		String result = PageService.printPage(url, currentPage, totalCount, pageBlock);

		model.addAttribute("members", members);
		model.addAttribute("result", result);
		model.addAttribute("currentPage", currentPage);
	}

	public MemberDTO userInfo(String id) {
		if (id == null || id.isEmpty()) {
			return null;
		}

		String sessionId = (String) session.getAttribute("id");
		if (sessionId.equals(id) == false && sessionId.equals("admin") == false)
			return null;

//		MemberDTO result = memberMapper.loginProc(id);
		return memberMapper.loginProc(id);
	}

	public String updateProc(MemberDTO member, String confirm) {
		if (member.getPw() == null || member.getPw().isEmpty()) {
			return "비밀번호를 입력하세요.";
		}

		if (member.getPw().equals(confirm) == false) {
			return "두 비밀번호를 일치하여 입력하세요.";
		}

		if (member.getUserName() == null || member.getUserName().isEmpty()) {
			return "이름을 입력하세요.";
		}

		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
		String cryptPassword = bpe.encode(member.getPw());
		member.setPw(cryptPassword);

		int result = memberMapper.updateProc(member);
		if (result == 1)
			return "회원 정보 수정 완료";
		return "회원 정보 수정 실패";
	}

	public String deleteProc(String id, String pw, String confirmPw) {
		if (pw == null || pw.isEmpty()) {
			return "비밀번호를 입력하세요.";
		}

		if (pw.equals(confirmPw) == false) {
			return "두 비밀번호를 일치하여 입력하세요.";
		}
		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
		MemberDTO member = memberMapper.loginProc(id);
		if (member != null && bpe.matches(pw, member.getPw())) {
			memberMapper.delete(id);
			return "회원 정보 삭제 완료";
		}
		return "비밀번호를 확인 후 다시 시도하세요.";
	}
	*/
	
	public String findIdByMobile(String userName, String mobile) {
		return memberMapper.findIdByMobile(userName, mobile);
	}

	public String findIdByEmail(String userName, String emailInput, String emailSelect) {
		String email = emailInput + emailSelect;
		return memberMapper.findIdByEmail(userName, email);
	}

}
