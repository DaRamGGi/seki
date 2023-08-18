package com.care.sekki.Recipe;

import java.io.IOException;

import java.sql.Timestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.care.sekki.S3.S3UploadService;
import com.care.sekki.board.BoardDTO;
import com.care.sekki.common.PageService;
import com.care.sekki.customerCenter.centerDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class RecipeService {
	//,MultipartFile profilePicture
	@Autowired private RecipeMapper recipemapper;
	@Autowired private HttpSession session;
	@Autowired private S3UploadService s3UploadService;
//---------------------------레시피 게시판------------------------
	public void recipeBoard(String cp, String search, Model model) {
		if (search == null) {
			search = "";
		}
		int currentPage = 1;
		try{
			currentPage = Integer.parseInt(cp);
		}catch(Exception e){
			currentPage = 1;
		}

		int pageBlock = 6; // 한 페이지에 보일 데이터의 수 
		int end = pageBlock * currentPage; // 테이블에서 가져올 마지막 행번호
		int begin = end - pageBlock + 1; // 테이블에서 가져올 시작 행번호

		ArrayList<RecipeBoardDTO> recipes = recipemapper.recipeBoard(begin, end, search);
		int totalCount = recipemapper.count(search);
		String url = "recipeBoard?&search="+search+"&currentPage=";
		String result = PageService.printPage(url, currentPage, totalCount, pageBlock);

		model.addAttribute("recipes", recipes);
		model.addAttribute("result", result);
		model.addAttribute("currentPage", currentPage);
		for(RecipeBoardDTO to : recipes) {
			System.out.println(to.getRe_no());
		System.out.println(to.getMainphoto());
		
		}
	}
	 
//---------------------------레시피 게시판------------------------

//----------------------------레시피 내용------------------------
	public RecipeBoardDTO recipeContent(String n) {
		long re_no = 0;
		try {
		    re_no = Long.parseLong(n);
		} catch (Exception e) {
		    return null;
		}

		RecipeBoardDTO recieContent = recipemapper.recipeContent(re_no);
		if (recieContent == null) {
			System.out.println("no가 널입니다.");
			return null;
		}
		System.out.println("re_no 값들어옴>? : " + recieContent.getRe_no());
		
		recieContent.setViews(recieContent.getViews() + 1);
		reHit(recieContent.getRe_no());
	
		return recieContent;
	}
	public List<MaterialDTO> recipeContent_ma(String n) {
		long re_no = 0;
		try {
		    re_no = Long.parseLong(n);
		} catch (Exception e) {
		    return null;
		}
		
		List<MaterialDTO> recipeMa = recipemapper.recipeMa(re_no);
	    if (recipeMa == null || recipeMa.isEmpty()) {
	        return null;
	    }
	    
	    for(MaterialDTO to : recipeMa) {
			System.out.println(to.getmaterialamount());
			System.out.println(to.getmaterialname());
	    }

	    return recipeMa; // 모든 결과를 그대로 반환
	}
	
	public List<StepDTO> recipeContent_step(String n) {
	    long re_no = 0;
	    try {
	        re_no = Long.parseLong(n);
	    } catch (Exception e) {
	        return null;
	    }

	    List<StepDTO> recipeSt = recipemapper.recipeSt(re_no);
	    if (recipeSt == null || recipeSt.isEmpty()) {
	        return null;
	    }

	    return recipeSt; // 모든 결과를 그대로 반환
	}
	
	public void reHit(Long re_no) {
		recipemapper.reHit(re_no);
	}
	
	public String commentProc(CommentDTO commentDto, HttpServletRequest request, HttpServletResponse response) {
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "로그인을 해주세요";
		}
		String profile = (String)session.getAttribute("profilePictureUrl");
		
		commentDto.setId(id);
		commentDto.setProfile(profile);
		Instant currentTime = Instant.now();
		commentDto.setWritten_time(Timestamp.from(currentTime));
		Object reNoObject = session.getAttribute("re_no");
		if (reNoObject instanceof Long) {
		    Long reNo = (Long) reNoObject;
		    commentDto.setRe_no(reNo);
		}

		System.out.println("re_no뜻냐 : " + commentDto.getRe_no());
		//recipemapper.insertComment(commentDto);
		return "댓글달기성공";
	}
	
//----------------------------댓글-------------------------------
	
//----------------------------레시피 내용------------------------
	
//----------------------------
	
//---------------------------레시피 작성------------------------------
	public String recipeProc(
			RecipeBoardDTO reciDto,
			HttpServletRequest request, HttpServletResponse response
			) {
		
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "로그인";
		}
		String profile = (String)session.getAttribute("profilePictureUrl");
		
		reciDto.setId(id);
		reciDto.setProfile(profile);
		// 서버의 현재 시간을 구해서 DTO에 설정
	    Instant currentTime = Instant.now();
	    reciDto.setWritten_time(Timestamp.from(currentTime));
	    
	    MultipartFile mainphotoUrl = reciDto.getMainphotoUrl();
	    try {
	        if (mainphotoUrl != null && !mainphotoUrl.isEmpty()) {
	            String mainphoto = s3UploadService.saveFile(mainphotoUrl, id); // 파일을 업로드하고 URL을 얻음
	            reciDto.setMainphoto(mainphoto); // 업로드한 파일의 URL을 DTO에 설정
	        } else {
	            reciDto.setMainphoto(""); // 이미지가 없을 경우 빈 문자열로 설정
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        reciDto.setMainphoto(""); // 예외 발생 시 빈 문자열로 설정하거나 다른 처리를 하세요.
	    }
	    System.out.println("유알엘 정보에요오오옹 : " + reciDto.getMainphoto());
	    
	    

	    recipemapper.insertRecipe(reciDto);
	    Long re_no = reciDto.getRe_no();
	    System.out.println(reciDto.getRe_no());
	    System.out.println("re no" + re_no);
	    String[] materialnames = request.getParameterValues("materialname");
	    System.out.println("재료 이름  :  " + materialnames);
	    String[] materialamounts = request.getParameterValues("materialamount");
	    System.out.println("재료 이름  :  " + materialamounts);

	    // 재료 데이터를 담을 리스트 생성
	    List<MaterialDTO> materials = new ArrayList<MaterialDTO>();

	    // 재료 데이터 배열을 DTO 리스트로 변환
	    for (int i = 0; i < materialnames.length; i++) {
	        MaterialDTO materialDTO = new MaterialDTO();
	        materialDTO.setRe_no(re_no);
	        System.out.println(materialDTO.getRe_no());
	        materialDTO.setmaterialname(materialnames[i]);
	        System.out.println(materialDTO.getmaterialname());
	        
	        materialDTO.setmaterialamount(materialamounts[i]);
	        System.out.println(materialDTO.getmaterialamount());
	        materials.add(materialDTO);
	        
	        
	    }
	    
	    
	    
	    for (MaterialDTO materialDTO : materials) {
	    	recipemapper.insertMaterial(materialDTO);
	    }
	    
	    String memberId = (String) session.getAttribute("id");
	    String[] stepTexts = request.getParameterValues("step_text");
	    List<MultipartFile> stepPhotoFiles = ((MultipartHttpServletRequest) request).getFiles("step_photoholder");
	    List<StepDTO> Steps = new ArrayList<>();
	    for (int i = 0; i < stepTexts.length; i++) {
	        StepDTO stepDTO = new StepDTO();
	        stepDTO.setStep_text(stepTexts[i]);
	        stepDTO.setRe_no(re_no);

	        if (stepPhotoFiles != null && stepPhotoFiles.size() > i) {
	            MultipartFile stepPhotoFile = stepPhotoFiles.get(i);

	            try {
	                if (!stepPhotoFile.isEmpty()) {
	                    String stepPhotoUrl = s3UploadService.saveFile(stepPhotoFile, memberId);
	                    stepDTO.setstep_photoholder(stepPhotoUrl);
	                } else {
	                    stepDTO.setstep_photoholder("");
	                }
	            } catch (IOException e) {
	                // IOException 발생 시 처리할 내용을 여기에 작성합니다.
	                e.printStackTrace(); // 예시로 에러 로그를 출력합니다.
	                stepDTO.setstep_photoholder(""); // 예외 발생 시 빈 문자열로 설정하거나 다른 처리를 하세요.
	            }
	        } else {
	            stepDTO.setstep_photoholder("");
	        }

	        Steps.add(stepDTO);
	    }
	    
	    for (StepDTO step : Steps) {
	    	recipemapper.insertStep(step);
        }

	    for (StepDTO stepDTO : Steps) {
	        System.out.println("스텝 텍스트 : " + stepDTO.getStep_text());
	        System.out.println("스탭 포토 : " + stepDTO.getstep_photoholder());
	    }



	      
	    System.out.println("title : " + reciDto.getTitle());
	    System.out.println("content : " + reciDto.getContent());
	    System.out.println("category : " + reciDto.getCategory());
	    System.out.println("cuisine : " + reciDto.getCuisine());
	    System.out.println("times : " + reciDto.getTimes());
	    System.out.println("degree : " + reciDto.getDegree());
	    System.out.println("tip : " + reciDto.getTip());
	    
	    System.out.println("time : " + reciDto.getWritten_time());
		System.out.println("id : " + id);
		
		return "람쥐";
	}
//---------------------------레시피 작성------------------------------
}
