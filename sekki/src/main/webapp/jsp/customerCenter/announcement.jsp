<%-- <%@page import="java.text.SimpleDateFormat"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<link href="css/customerCenter.css" rel="stylesheet"/> 	
</head>
<body>
<c:import url="/header"></c:import>

<div id="wrap">
	<div class="searchDiv">
		<h2>공지사항</h2>
		<form action="announcement">
			<input type="text" name="search" />
			<input type="submit" value="검색" />
		</form>
	</div>
	<table class="announceTable">
		<colgroup>
			<col width="10%"></col>
			<col width="45%"></col>
			<col width="15%"></col>
			<col width="25%"></col>
			<col width="5%"></col>
		</colgroup>
		<thead>
		<tr>
			<th>No.</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>조회수</th>
		</tr>
		</thead>
		<tbody>
			<c:forEach var="announcement" items="${ announcements}">
				<tr>
					<td>${announcement.num}</td>
					<td class="announceHover" onclick="location.href='announcementContent?num=${announcement.num }'">
						${announcement.title }
					</td>
					<td>${announcement.writer }</td>
					<td>${announcement.writeDate }</td>
					<td>${announcement.hits }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pages"> ${result}	</div>
	<div class="announcebtnDiv">
		<button type="button" onclick="location.href='writeAnnouncement'">공지사항 등록</button>
	</div>
</div>


<c:import url="/footer"/>
</body>
</html>