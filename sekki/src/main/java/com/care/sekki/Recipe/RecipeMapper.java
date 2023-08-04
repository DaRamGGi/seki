package com.care.sekki.Recipe;

import org.apache.ibatis.annotations.Mapper;

import com.care.sekki.board.BoardDTO;

@Mapper
public interface RecipeMapper {
	void recipeProc(RecipeBoardDTO ricipeDto);
}
