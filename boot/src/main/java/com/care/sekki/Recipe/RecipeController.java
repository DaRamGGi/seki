package com.care.sekki.Recipe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RecipeController {

	@GetMapping("/recipeBoard")
    public String recipeBoard() {
        return "recipe/recipeBoard";
    }
	
	@PostMapping("/recipeBoard")
	public String recipeBoardd() {
		return "recipe/recipeBoard";
	}
	
}
