<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style type="text/css">
	a {text-decoration: none; color:black;}
	ul {padding: 20px;}
	ul li {display: inline; padding: 15px;}
	.main_div{height: 150px; padding-top : 80px;}
</style>    

<script src="dbQuiz.js"></script>
<link href="css/common.css" rel="stylesheet"/> 	

<div id="header">
	<div class="mainLogo">
		<a href="index">
			<img src="image/logo.png" alt="logo" />
		</a>
	</div>
	<ul class="nav">
		<li><a href="${context }index">HOME</a></li>
		<li><a href="${context }register">Register</a></li>
		<li><a href="${context }login">Login</a></li>
		<li><a href="${context }memberInfo">MemberInfo</a></li>
		<li><a href="${context }logout">Logout</a></li>
		<li><a href="${context }boardForm">Board</a></li>
	</ul>
</div>
<hr>
<c:url var="context" value="/"/>








