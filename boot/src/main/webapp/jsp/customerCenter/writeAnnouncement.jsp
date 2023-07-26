<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 등록</title>
<link href="css/customerCenter.css" rel="stylesheet"/> 
</head>
<body>
<c:import url="/header"></c:import>
	<div id="wrap">
	<h2>공지사항 등록</h2>
		<form action="writeAnnouncementProc" method='post' enctype="multipart/form-data" id="f">
		<div class="writeForm">
			<table class="writeTable">
			<tr>
				<th>제목</th>
				<td><input type="text" name="title" id="title" placeholder="제목(최대 45자)"></td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<textarea rows="10" cols="30" name="content" id="content" placeholder="내용을 입력하세요."></textarea>
				</td>
			</tr>
			<tr>
				<th>파일첨부</th>
				<td><input type="file" name="upfile"></td>
			</tr>
		</table>
		</div>
	
		<div class="writebtnDiv">
			<input type="button" value="글쓰기" onclick="nullCheck()" class="writeAnnounceBtn">
			<input type="button" value="목록" onclick="location.href='announcement'" class="Listbtn">
		</div>
	</form>
	</div>
<c:import url="/footer"/>
</body>
</html>