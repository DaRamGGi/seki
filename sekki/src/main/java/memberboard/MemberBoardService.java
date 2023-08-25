package memberboard;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.care.sekki.common.PageService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class MemberBoardService {
	@Autowired private MemberBoardMapper memberboardMapper;
	@Autowired private HttpSession session;
	
	public void memberboardForm(String cp, Model model) {
		int currentPage = 1;
		try{
			currentPage = Integer.parseInt(cp);
		}catch(Exception e){
			currentPage = 1;
		}
		
		int pageBlock = 10; // 한 페이지에 보일 데이터의 수 
		int end = pageBlock * currentPage; // 테이블에서 가져올 마지막 행번호
		int begin = end - pageBlock + 1; // 테이블에서 가져올 시작 행번호
	
		ArrayList<MemberBoardDTO> memberboard = memberboardMapper.memberboardForm(begin, end);
		int totalCount = memberboardMapper.count();
		String url = "memberboardForm?currentPage=";
		String result = PageService.printPage(url, currentPage, totalCount, pageBlock);
		
		
		model.addAttribute("memberboard", memberboard);
		model.addAttribute("result", result);
		model.addAttribute("currentPage", currentPage);
	}

	
	
	public String memberboardWriteProc(MultipartHttpServletRequest multi) {

		String id = (String) session.getAttribute("id");
		if (id == null || id.isEmpty()) {
			return "로그인";
		}

		MemberBoardDTO memberboard = new MemberBoardDTO();
		memberboard.setId(id);
		memberboard.setTitle(multi.getParameter("title"));
		memberboard.setContent(multi.getParameter("content"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		memberboard.setWriteDate(sdf.format(new Date()));
		memberboard.setFileName("");

		if (memberboard.getTitle() == null || memberboard.getTitle().isEmpty()) {
			return "제목을 입력하세요.";
		}

		MultipartFile file = multi.getFile("upfile");
		String fileName = file.getOriginalFilename();
		if (file.getSize() != 0) {
			// 파일의 중복을 해결하기 위해 시간의 데이터를 파일이름으로 구성함.
			sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			Calendar cal = Calendar.getInstance();
			fileName = sdf.format(cal.getTime()) + fileName;
			memberboard.setFileName(fileName); 


			String fileLocation = "C:\\javas\\upload\\";
			File save = new File(fileLocation + fileName);

			try {
				// 서버가 저장한 업로드 파일은 임시저장경로에 있는데 개발자가 원하는 경로로 이동
				file.transferTo(save);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		memberboardMapper.memberboardWriteProc(memberboard);
		return "게시글 작성 완료";
	}

	
	
	public MemberBoardDTO memberboardContent(String n) {
		int no = 0;
		try{
			no = Integer.parseInt(n);
		}catch(Exception e){
			return null;
		}
		
		MemberBoardDTO memberboard = memberboardMapper.memberboardContent(no);
		if(memberboard == null)
			return null;
		
		memberboard.setHits(memberboard.getHits()+1);
		incHit(memberboard.getNo());

		System.out.println("memberboard.getFileName() : " + memberboard.getFileName());
		System.out.println("memberboard.getFileName() : " + memberboard.getFileName().isEmpty());
		if(memberboard.getFileName() != null && memberboard.getFileName().isEmpty() == false) {
			String fn = memberboard.getFileName();
			String[] fileName = fn.split("-", 2);
			memberboard.setFileName(fileName[1]);
		}
		return memberboard;
	}
	
	
	public void incHit(int no) {
		memberboardMapper.incHit(no);
	}

	
	
	public boolean memberboardDownload(String n, HttpServletResponse res) {
		int no = 0;
		
		try{
			no = Integer.parseInt(n);
		}catch(Exception e){
			return false;
		}
		
		String fileName = memberboardMapper.memberboardDownload(no);
		if(fileName == null)
			return false;
		
		String location = "C:\\javas\\upload\\";
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

	
	
	public MemberBoardDTO memberboardModify(String n) {
		int no = 0;
		try{
			no = Integer.parseInt(n);
		}catch(Exception e){
			return null;
		}
		
		MemberBoardDTO memberboard = memberboardMapper.memberboardContent(no);
		if(memberboard == null)
			return null;

		if(memberboard.getFileName() != null && memberboard.getFileName().isEmpty() == false) {
			String fn = memberboard.getFileName();
			String[] fileName = fn.split("-", 2);
			memberboard.setFileName(fileName[1]);
		}
		return memberboard;
	}
	
	
	
	
	public String memberboardModifyProc(MemberBoardDTO memberboard) {
		if(memberboard.getTitle() == null || memberboard.getTitle().isEmpty())
			return "제목을 입력하세요.";
		
		memberboardMapper.memberboardModifyProc(memberboard);
		return "게시글 수정 완료";
	}

	
	
	
	
	public String memberboardDeleteProc(String n) {
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "로그인";
		}
		
		int no = 0;
		try{
			no = Integer.parseInt(n);
		}catch(Exception e){
			return "게시글 번호에 문제가 생겼습니다.";
		}
		
		MemberBoardDTO memberboard = memberboardMapper.memberboardContent(no);
		if(memberboard == null)
			return "게시글 번호에 문제가 생겼습니다.";
		
		if(id.equals(memberboard.getId()) == false)
			return "작성자만 삭제 할 수 있습니다.";
		
		memberboardMapper.memberboardDeleteProc(no);
		
		String path = "C:\\javas\\upload\\" + memberboard.getFileName();
		File file = new File(path);
		if(file.exists() == true) {
			file.delete();
		}
		
		return "게시글 삭제 완료";
	}

	
}









