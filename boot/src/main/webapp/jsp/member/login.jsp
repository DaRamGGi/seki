<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/header" />
<div align="center" class="main_div">
	<h1>로그인</h1>
	<table ><tr><td>
	<form action="loginProc" method="post" id="f">
		<input type="text" name="id" placeholder="아이디" id="id" class="sub_input" ><br>
		<input type="password" name="pw" placeholder="비밀번호" id="pw" class="sub_input"><br>
		<input type="button" value="로그인" onclick="loginCheck()" class="btn">
		<input type="button" value="취소" onclick="location.href='index.jsp'" class="btn"><br>
	</form>
	</td></tr>
	<!-- 
		# 카카오 이미지 URL #
		https://developers.kakao.com/tool/demo/login/login?method=dynamic
		
		# 인가 코드 요청 #
		https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-code-request
	 -->
	<tr><td>
		<a href="https://kauth.kakao.com/oauth/authorize?response_type=code&
		client_id=2e151bc9c1174ea0e8836b77c28803c3&
		redirect_uri=http://localhost/kakaoLogin">
			<img src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg" />
		</a>
	</td></tr>
	</table>
</div>
<c:import url="/footer" />











