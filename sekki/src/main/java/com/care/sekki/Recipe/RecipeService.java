package com.care.sekki.Recipe;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.care.sekki.S3.S3UploadService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class RecipeService {
	//,MultipartFile profilePicture
	@Autowired private RecipeMapper recipemapper;
	@Autowired private HttpSession session;
	@Autowired private S3UploadService s3UploadService;

	public String recipeProc(
			RecipeBoardDTO reciDto,
			HttpServletRequest request, HttpServletResponse response
			) {
		
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "로그인";
		}
		
		reciDto.setId(id);
		// 서버의 현재 시간을 구해서 DTO에 설정
	    Instant currentTime = Instant.now();
	    reciDto.setWritten_time(Timestamp.from(currentTime));

	    recipemapper.insertRecipe(reciDto);
	    Long re_no = reciDto.getRe_no();
	    System.out.println(reciDto.getRe_no());
	    System.out.println("re no" + re_no);
	    String[] materialNames = request.getParameterValues("materialName");
	    System.out.println("재료 이름  :  " + materialNames);
	    String[] materialAmounts = request.getParameterValues("materialAmount");
	    System.out.println("재료 이름  :  " + materialAmounts);

	    // 재료 데이터를 담을 리스트 생성
	    List<MaterialDTO> materials = new ArrayList<MaterialDTO>();

	    // 재료 데이터 배열을 DTO 리스트로 변환
	    for (int i = 0; i < materialNames.length; i++) {
	        MaterialDTO materialDTO = new MaterialDTO();
	        materialDTO.setRe_no(re_no);
	        System.out.println(materialDTO.getRe_no());
	        materialDTO.setMaterialName(materialNames[i]);
	        System.out.println(materialDTO.getMaterialName());
	        
	        materialDTO.setMaterialAmount(materialAmounts[i]);
	        System.out.println(materialDTO.getMaterialAmount());
	        materials.add(materialDTO);
	        
	        
	    }
	    
	    
	    
	    for (MaterialDTO materialDTO : materials) {
	    	recipemapper.insertMaterial(materialDTO);
	    }
	    
	    String memberId = (String) session.getAttribute("id");
	    String[] stepTexts = request.getParameterValues("step_text");
	    List<MultipartFile> stepPhotoFiles = ((MultipartHttpServletRequest) request).getFiles("step_photoinput");
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
	                    stepDTO.setStep_photoinput(stepPhotoUrl);
	                } else {
	                    stepDTO.setStep_photoinput("");
	                }
	            } catch (IOException e) {
	                // IOException 발생 시 처리할 내용을 여기에 작성합니다.
	                e.printStackTrace(); // 예시로 에러 로그를 출력합니다.
	                stepDTO.setStep_photoinput(""); // 예외 발생 시 빈 문자열로 설정하거나 다른 처리를 하세요.
	            }
	        } else {
	            stepDTO.setStep_photoinput("");
	        }

	        Steps.add(stepDTO);
	    }
	    
	    for (StepDTO step : Steps) {
	    	recipemapper.insertStep(step);
        }

	    for (StepDTO stepDTO : Steps) {
	        System.out.println("스텝 텍스트 : " + stepDTO.getStep_text());
	        System.out.println("스탭 포토 : " + stepDTO.getStep_photoinput());
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
}
