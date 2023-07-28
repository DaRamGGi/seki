<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<link href="css/customerCenter.css" rel="stylesheet"/> 
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
</head>
<body>
<c:import url="/header"></c:import>
<div id="wrap">
	<div class="viewAnnounce">
		<h2>${announcement.title }</h2>
		<p>
			<span class="material-symbols-outlined">account_circle</span>
			<span>${announcement.writer }</span>
		</p>
		<p>작성일시 ${announcement.writeDate }${announcement.writeTime }</p>
		<hr>
		<p>${announcement.content }</p>
		<hr>
		<p class="fileName"> ${announcement.files } 파일 </p>
		
		<div height="100px"></div>
		
		<ul>
			<li>하트</li>
			<li>${announcement.likes }</li>
			<li>하트</li>
			<li>댓글수</li>
		</ul>
	</div>
</div>
<c:import url="/footer"></c:import>
</body>
</html>