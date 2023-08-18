package com.care.sekki.Recipe;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class RecipeController {
	
	@Autowired
	private RecipeService recipeService;
	@Autowired private HttpSession session;
	
	@RequestMapping("/recipeBoard")
    public String recipeBoard(@RequestParam(value="currentPage", required = false)String cp,
			String search, Model model) {
		recipeService.recipeBoard(cp, search ,model);
        return "recipe/recipeBoard";
    }
	
	
	@GetMapping("/recipeBoardWrite")
    public String recipeBoardWrite() {
		if(session.getAttribute("id") == null ) {
			return "redirect:login";
		}
        return "recipe/recipeBoardWrite";
    }
	
	@PostMapping("recipeProc")
	public String recipeProc(RecipeBoardDTO recipeDto,HttpServletRequest request, HttpServletResponse response) {
		recipeService.recipeProc(recipeDto,request, response);
		
		return "recipe/recipeBoardWrite";
	}
	@RequestMapping("recipeBoardContent")
	public String recipeBoardContent(
			@RequestParam(value="num", required = false)String n, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		RecipeBoardDTO reciDto = recipeService.recipeContent(n);
		
		if(reciDto == null) 
			return "redirect:recipeBoard"; 
		
		model.addAttribute("recipeCon", reciDto);
		
		//카테고리 값변환
		if(reciDto.getCategory().equals("kor")) {
			reciDto.setCategory("한식");
		}else if(reciDto.getCategory().equals("jap")) {
			reciDto.setCategory("일식");
		}else if(reciDto.getCategory().equals("chi")) {
			reciDto.setCategory("중식");
		}else
			reciDto.setCategory("양식");
		System.out.println("re_no 들어가나?  :  " + reciDto.getRe_no());
		session.setAttribute("re_no",reciDto.getRe_no());
		
		//시간 값변환
		if(reciDto.getTimes().equals("5")) {
			reciDto.setTimes("5분 이내");
		}else if(reciDto.getTimes().equals("30")){
			reciDto.setTimes("30분 이내");
		}else if(reciDto.getTimes().equals("60")){
			reciDto.setTimes("1시간 이내");
		}else
			reciDto.setTimes("2시간 이상");

		//몇인분 값변환
		if(reciDto.getCuisine().equals("1")) {
			reciDto.setCuisine("1인분");
		}else if(reciDto.getCuisine().equals("2")) {
			reciDto.setCuisine("2인분");
		}else
			reciDto.setCuisine("3인분");
			
		//난이도 값변환
		if(reciDto.getDegree().equals("1")) {
			reciDto.setDegree("아무나");
		}else if(reciDto.getDegree().equals("2")) {
			reciDto.setDegree("초급");
		}else if(reciDto.getDegree().equals("3")) {
			reciDto.setDegree("중급");
		}else if(reciDto.getDegree().equals("4")) {
			reciDto.setDegree("고급");
		}
		
		

		/*
		 * RecipeBoardDTO reciDto = recipeService.recipeContent(n);
		
		if(reciDto == null) 
			return "redirect:recipeBoard"; 
		
		model.addAttribute("recipeCon", reciDto);
		
		 * 
		 */
		List<MaterialDTO> reciMa = recipeService.recipeContent_ma(n);

		if (reciMa == null)
		    return "redirect:recipeBoard";

		model.addAttribute("reciMa", reciMa);

		List<StepDTO> reciStepList = recipeService.recipeContent_step(n); // 수정된 부분

		if (reciStepList == null || reciStepList.isEmpty())
		    return "redirect:recipeBoard";

		model.addAttribute("reciStepList", reciStepList); // 모든 결과 리스트를 모델에 추가

		return "recipe/recipeBoardContent";
	}
	@GetMapping("commentProc")
	public String commentProc(HttpSession session) {

		return "rerer";
	}
	
}
