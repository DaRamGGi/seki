package com.care.sekki.Recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class RecipeController {
	
	@Autowired
	private RecipeService recipeService;
	@Autowired private HttpSession session;
	
	@GetMapping("/recipeBoard")
	public String recipeBoard() {
		return "recipe/recipeBoard";
	}

	@PostMapping("/recipeBoard")
	public String recipeBoardd() {
		return "recipe/recipeBoard";
	}

	@GetMapping("/recipeBoardWrite")
<<<<<<< HEAD
	public String recipeBoardWrite() {
		return "recipe/recipeBoardWrite";
	}

	@PostMapping("/recipeBoardWrite")
	public String recipeBoardWritee() {
=======
    public String recipeBoardWrite() {
		if(session.getAttribute("id") == null ) {
			return "redirect:login";
		}
        return "recipe/recipeBoardWrite";
    }
	
	@PostMapping("recipeProc")
	public String recipeProc(RecipeBoardDTO recipeDto,HttpServletRequest request, HttpServletResponse response) {
		recipeService.recipeProc(recipeDto,request, response);
		
>>>>>>> refs/remotes/origin/gyutae
		return "recipe/recipeBoardWrite";
	}

}
