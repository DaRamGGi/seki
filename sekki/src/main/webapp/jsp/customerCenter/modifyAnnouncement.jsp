<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 수정</title>
<link href="css/customerCenter.css" rel="stylesheet"/> 
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
   /*  var announcementNum = ${announcement.num}; */
    function modifyCheck(){
    	let title = document.getElementById('title');
    	let content = document.getElementById('content');
    	
    	if(title.value == ""){
    		alert('제목을 입력하세요.');
    	}else if(content.value == ""){
    		alert('내용을 입력하세요.');
    	}else{
    		var result = confirm("수정하시겠습니까?");
    	}
    	
    	if(result){
    		location.href='modifyAnnouncementProc?num=${announcement.num}'
    	}
    }
</script>
</head>
<body>

<c:import url="/header"></c:import>
<div id="wrap">
	<h2>공지사항 수정</h2>
	<form action="modifyAnnouncementProc" method='post' enctype="multipart/form-data" id="f">
    	<div class="writeForm">
	       	 <table class="writeTable">
	            <tr>
	                <th>제목 수정</th>
	                <td><input type="text" name="title" id="title" value="${announcement.title}"/></td>
	            </tr>
	            <tr>
	                <th>내용 수정</th>
	                <td>
	                    <textarea rows="5" cols="50" name="content" id="content">${announcement.content}</textarea>
	                </td>
	            </tr>
	            <tr class="fileModify">
	                <th>업로드한 파일</th>
	                <td>
		               	<ul>
		                	<c:choose>
		                	<c:when test="${empty announcement.files }">
		                		<li>없음</li>
		                	</c:when>
		                	<c:otherwise>
		                		<li>${announcement.files}</li>
		                	</c:otherwise>
		                	</c:choose>
		                	<li>
								 <label for="file">파일수정</label> 
								 <input type="file" id="file" name="upfile">
		                		 <input class="upload-name" value="첨부파일" placeholder="첨부파일">
		                	</li>
		                </ul>
	                </td>
	            </tr>
	        </table>
	    </div>

	    <div class="writebtnDiv">
	        <input type="button" value="수정" onclick="location.href='modifyAnnouncementProc?num=${announcement.num}'">
	        <input type="button" value="목록" onclick="history.back()">
	    </div>
	</form>
</div>
<c:import url="/footer"></c:import>
</body>
</html>