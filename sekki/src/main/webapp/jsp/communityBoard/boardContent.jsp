<%@ page import="java.io.OutputStream"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />

<c:import url="/header" />

<script>
	function deleteCheck(){
		result = confirm('진짜로 삭제하겠습니까?');
		if(result == true){
			location.href='boardDeleteProc?no=${board.no}'
		}
	}
	
	function likeProc(){
		
		location.href='likeProc?no=${board.no}'
	}
</script>
<div align="center" class="sub_div">
	<table class="boardContent">
		<tr>
			<td>
				<span class="material-symbols-outlined">menu</span>
			</td>
			<td>
				<span class="material-symbols-outlined" id="wifi">wifi</span>
			</td>
			<td>
				<span class="material-symbols-outlined" id="battery">battery_horiz_050</span>
			</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${board.id }</td>
			<th>조회수</th>
			<td>${board.hits }</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${board.writeDate }</td>
			<th>다운로드</th>
			<c:choose>
				<c:when test="${empty board.fileName }">
					<td></td>
				</c:when>
				<c:otherwise>
					<td onclick="location.href='boardDownload?no=${board.no }'">
						${board.fileName }
					</td>
				</c:otherwise>
			</c:choose>
		</tr>
		<tr>
			<th colspan="4" id="boardTitle">${board.title }</th>
		</tr>
		<tr>
			<td colspan="4">${board.content }</td>
		</tr>
		<tr>
			<td><span class="material-symbols-outlined" onclick="likeProc()">thumb_up</span></td>
			<td>${board.likes }</td>
		</tr>
	</table>
		<div>
			<button type="button" onclick="location.href='boardForm'">목록</button>
			<button type="button" onclick="location.href='boardModify?no=${board.no }'" id="modifyBtn">수정</button>
			<button type="button" onclick="deleteCheck()" id="deleteBtn">삭제</button> 
		</div>
</div>
<c:import url="/footer" />
