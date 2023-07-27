<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
					<li>

					</li>
				</c:forEach>
			</ul>


				<table class="boardForm">
					<tr>
						<th width="40">No.</th>
						<th width="550">제목</th>
						<th width="110">작성자</th>
						<th width="120">작성일</th>
						<th width="70">조회수</th>
					</tr>

					<c:forEach var="board" items="${ boards}">
						<tr>
							<td>${board.no }</td>
							<td onclick="location.href='boardContent?no=${board.no }'" id="boardTitle">
								${board.title }
							</td>
							<td>${board.id }</td>
							<td>${board.writeDate }</td>
							<td>${board.hits }</td>
						</tr>
					</c:forEach>
				</table>
				<div class="boardList">
					${result }
					<button type="button" onclick="location.href='boardWrite'">글쓰기</button>
				</div>
		</c:otherwise>
	</c:choose>
</div>
<c:import url="/footer" />

