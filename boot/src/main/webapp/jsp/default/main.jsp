<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<div align="center" class="main_div">
	<h1>자취생들을 위한 레시피!<br>
		냉장고 재료만으로 내요리를 만들어 보세요!
	</h1>
	<form>
		<input type="text" placeholder="재료" class="main_input" /><br>
		<input type="text" placeholder="재료" class="main_input" /><br>
		<input type="text" placeholder="재료" class="main_input" /><br>
		<input type="submit" value="요리하러 가기" class="btn" style="width:150px;" />
	</form><br>
	 ${sessionScope.id }<br>
</div>