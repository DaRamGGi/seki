<%@ page import="java.io.OutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/header" />

<link href="css/recipes.css" rel="stylesheet"/> 	

<div align="center">
	<ul class="cate">
		<li>
		<a href=""><span>한식</span></a>
		</li>
		<li>
		<a href=""><span>중식</span></a>
		</li>
		<li>
		<a href=""><span>일식</span></a>
		</li>
		<li>
		<a href=""><span>양식</span></a>
		</li>
	</ul>
</div>


<div align="center">
	<ul>
		<li>
			<a href="${context }recipeBoardWrite">
				<span>글 작성하기</span>
			</a>
		</li>

	</ul>
</div>

<c:import url="/footer" />