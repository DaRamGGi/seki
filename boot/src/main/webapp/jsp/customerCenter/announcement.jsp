<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<link href="css/customerCenter.css" rel="stylesheet"/> 	
</head>
<body>
<c:import url="/header"></c:import>

<div id="wrap">
	<h2>공지사항</h2>
 	<%-- <c:choose>
		<c:when test="${empty boards }">
			<h1> 등록된 데이터가 존재하지 않습니다. </h1>
		</c:when>
	<c:otherwise> --%>
		<table border=1>
			<tr>
				<th width="50">No.</th>
				<th width="250">제목</th>
				<th width="100">작성자</th>
				<th width="100">작성일</th>
				<th width="60">조회수</th>
			</tr>
			
			<tr>
				<td>1</td>
				<td>제목</td>
				<td>작성자</td>
				<td>2023-07-04</td>
				<td>1</td>
			</tr>
		</table>
	<%-- </c:otherwise>
	</c:choose> --%>
</div>


<c:import url="/footer"/>
</body>
</html>