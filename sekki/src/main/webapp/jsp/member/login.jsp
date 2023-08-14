<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<script src="/js/member.js"></script>
<link href="css/member.css" rel="stylesheet"/> 
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gamja+Flower&display=swap" rel="stylesheet">	
</head>
<body>
<c:import url="/header"></c:import>

<div id="wrap">
	<form action="loginProc" class="loginProc" method="post" id="f">
		<table class="loginTable">
			<colgroup>
				<col width="50%"></col>
				<col width="50%"></col>
			</colgroup>
			<tr>
				<td><h2>반갑습니다.<br><span class="titleSpan">자취세끼</span>입니다.</h2></td>
			</tr>
			<tr>
				<td colspan="2"><input type="text"  name="id" placeholder="아이디" id="id"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="password" name="pw" placeholder="비밀번호" id="pw"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="button" value="로그인" onclick="loginCheck()"></td>
			</tr>
			<tr class="textBox">
				<td><p>아이디/비밀번호 찾기</p></td>
				<td><p onclick="location.href='agreeCondition'">회원가입</p></td>
			</tr>
			<tr>
				<td colspan="2">
					<a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=2e151bc9c1174ea0e8836b77c28803c3&redirect_uri=http://localhost/kakaoLogin">
						<img src="https://t1.daumcdn.net/cfile/tistory/99BEE8465C3D7D1214"/>
					</a>
				</td>
			</tr>
		</table>
	</form>
</div>

<c:import url="/footer"/>
</body>
</html>