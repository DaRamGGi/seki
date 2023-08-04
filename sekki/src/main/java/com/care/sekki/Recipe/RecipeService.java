package com.care.sekki.Recipe;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.care.sekki.member.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class RecipeService {
	//,MultipartFile profilePicture
	@Autowired private RecipeMapper recipemapper;
	@Autowired private HttpSession session;

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

	    String[] materialNames = request.getParameterValues("materialName");
	    String[] materialAmounts = request.getParameterValues("materialAmount");

	    // 재료 데이터를 담을 리스트 생성
	    List<MaterialDTO> materials = new ArrayList<MaterialDTO>();

	    // 재료 데이터 배열을 DTO 리스트로 변환
	    for (int i = 0; i < materialNames.length; i++) {
	        MaterialDTO materialDTO = new MaterialDTO();
	        materialDTO.setMaterialName(materialNames[i]);
	        materialDTO.setMaterialAmount(materialAmounts[i]);
	        materials.add(materialDTO);
	        
	    }
	    
	    
	    for (MaterialDTO materialDTO : materials) {
	        System.out.println(materialDTO.getMaterialName());
	        System.out.println(materialDTO.getMaterialAmount());
	    }
	    System.out.println("재료 : " + reciDto.getMaterials());
	    /*  확인작업  지금 stepText, stepPhoto 안들어감
	    System.out.println("title : " + reciDto.getTitle());
	    System.out.println("content : " + reciDto.getContent());
	    System.out.println("category : " + reciDto.getCategory());
	    System.out.println("cuisine : " + reciDto.getCuisine());
	    System.out.println("times : " + reciDto.getTimes());
	    System.out.println("degree : " + reciDto.getDegree());
	    System.out.println("tip : " + reciDto.getTip());
	    System.out.println("MaterialName : " + reciDto.getMaterialName());
	    System.out.println("MaterialAmount : " + reciDto.getMaterialAmount());
	    System.out.println("stepText : " + reciDto.getStepText());
	    System.out.println("stepPhoto" + reciDto.getStepPhotoHolder());
	    
	    System.out.println("time : " + reciDto.getWritten_time());
		System.out.println("id : " + id);
		*/
		return "람쥐";
	}
}
