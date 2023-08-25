package com.care.sekki.Recipe;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.care.sekki.board.BoardDTO;

@Mapper
public interface RecipeMapper {
	void insertRecipe(RecipeBoardDTO ricipeDto);
	void insertMaterial(MaterialDTO materialDTO);
	void insertStep(StepDTO step);

}
