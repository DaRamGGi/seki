package com.care.sekki.Recipe;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.care.sekki.board.BoardDTO;

@Mapper
public interface RecipeMapper {
	ArrayList<RecipeBoardDTO> recipeBoard(@Param("begin")int begin, @Param("end")int end, 
			@Param("search")String search);

	int count(@Param("search")String search);
	void insertRecipe(RecipeBoardDTO ricipeDto);
	void insertMaterial(MaterialDTO materialDTO);
	void insertStep(StepDTO step);

	RecipeBoardDTO recipeContent(long re_no);

	void reHit(Long re_no);

	List<MaterialDTO> recipeMa(long re_no);

	List<StepDTO> recipeSt(long re_no);

}
