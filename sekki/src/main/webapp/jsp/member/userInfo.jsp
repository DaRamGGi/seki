<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/header" />

<div align="center" class="sub_div">
	<h1>개인 정보</h1>
	아이디 : ${member.id } <br> 
	비밀번호 : ${member.pw }<br>
	이름 : ${member.userName }<br>
	주소 : ${member.address } <br>
	전화번호 : ${member.mobile } <br><br>
	구독하기 : ${subscriber.is_Subscribed }<br>
	<button type="button" onclick="toggleSubscription(this);" class="subscription-btn">&#x2764;</button>
	<button type="button" onclick="location.href='update'" class="btn">회원 수정</button>
	<button type="button" onclick="location.href='delete'" class="btn">회원 삭제</button>
</div>	

<c:import url="/footer"/>






