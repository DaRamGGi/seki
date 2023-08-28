<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/header" />

<div align="center" class="main_div">
	<h1>게시글 목록</h1>
		<c:choose>
			<c:when test="${empty memberboards }">
				<h1> 등록된 데이터가 존재하지 않습니다. </h1>
			</c:when>
			<c:otherwise>
				<table border=1>
					<tr>
						<th width="50">No.</th>
						<th width="250">제목</th>
						<th width="100">작성자</th>
						<th width="100">작성일</th>
						<th width="60">조회수</th>
					</tr>
					
					<c:forEach var="memberboard" items="${memberboards}">
						<tr>
							<td>${memberboard.no}</td>
							<td onclick="location.href='memberboardContent?no=${memberboard.no}'">
								${memberboard.title}
							</td>
							<td>${memberboard.id}</td>
							<td>${memberboard.writeDate}</td>
							<td>${memberboard.hits}</td>
						</tr>
					</c:forEach>
					<tr>
									
						<td colspan="10">
							${result}
						</td>
						<td><button type="button" onclick="location.href='memberboardWrite'">글쓰기</button></td>
					</tr>
				</table>
		</c:otherwise>
	</c:choose>
</div>





<c:import url="/footer" />










