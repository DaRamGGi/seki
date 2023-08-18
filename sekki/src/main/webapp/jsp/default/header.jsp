<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style type="text/css">
	a {text-decoration: none; color:black;}
	ul {padding: 20px;}
	ul li {display: inline; padding: 15px;}
	.main_div{height: 150px; padding-top : 80px;}
</style>    

<script src="dbQuiz.js"></script>
<link href="css/reset.css" rel="stylesheet" type="text/css" /> 	

<div id="header">
	<div class="mainLogo">
		<a href="index">
			<img src="image/logo.png" alt="logo" />
		</a>
	</div>
	<ul class="nav">
		<li><a href="${context }login">로그인</a></li>
		<li><a href="${context }recipe">레시피</a></li>
		<li><a href="${context }mypage">마이페이지</a></li>
		<li><a href="${context }communityBoard">커뮤니티</a></li>
		<li><a href="${context }memberInfo">스토어</a></li>
		<li><a href="${context }announcement">고객센터</a></li>
	</ul>
</div>
<hr>
<c:url var="context" value="/"/>