<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<script src="/js/member.js"></script>
<link href="css/member.css" rel="stylesheet"/>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" /> 
</head>
<body>
<c:import url="/header"></c:import>
<div id="visual">
		<h1>회원정보 찾기</h1>
	</div>
<div id="wrap">
	<table class="findTable">
		<tr>
			<td>
				<ul>
					<li class="findIdTab" onclick="location.href='findId'">아이디 찾기</li>
					<li class="findPwTab selected" >비밀번호 찾기</li>
				</ul>
			</td>
		<tr>
		<tr><td><h2>임시 비밀번호 발급</h2></td></tr>
		<tr>
			<td class="findPwIdInputTd">
				<p>* 임시 비밀번호는 가입시 입력하신 휴대폰/이메일로 전송됩니다.</p>
				<h4>아이디</h4>
				<input type="text" id="id" name="id" placeholder="아이디 입력">
				<h4>임시비밀번호를 어디로 보내드릴까요?</h4>
				<div class="findPwCheckboxDiv">
					<input type="checkbox">휴대폰으로 받기<br>
					<input type="checkbox">이메일로 받기<br>
				</div>
				<input type="button" value="임시비밀 번호 발급">
			</td>
		</tr>
		
	</table>
</div>

<c:import url="/footer"/>
</body>
</html>