<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />

<c:import url="/header" />

<div align="center" class="sub_div">
	<h1>
	<a href="boardForm">공동구매</a> ㆍ <span>밥친구</span>
	</h1>
		<c:choose>
			<c:when test="${empty boards }">
				<h1> 등록된 데이터가 존재하지 않습니다. </h1>
			</c:when>
			<c:otherwise>
			<ul class="boardForm">
				<c:forEach var="board" items="${ boards}">
					<li onclick="location.href='boardContent?no=${board.no }'" >
						<div id="top">
							<span>${board.no } </span>
							<span class="material-symbols-outlined">account_circle</span>
							 <span>${board.id }</span>
							<span>${board.title }</span>
						</div>
						<div id="down">
							<span>${board.content }</span>
							<span>${board.writeDate }</span>
							<span>${board.hits }</span>
						</div>
					</li>
				</c:forEach>
			</ul>
			
				<div class="boardList">
					${result }
					<button type="button" onclick="location.href='boardWrite'">글쓰기</button>
				</div>
		</c:otherwise>
	</c:choose>
</div>
<c:import url="/footer" />

