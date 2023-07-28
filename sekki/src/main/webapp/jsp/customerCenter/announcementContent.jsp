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
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Sharp:opsz,wght,FILL,GRAD@48,400,0,0" />
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Sharp:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<script>
function fileDownload(){
	result = confirm('파일을 다운로드 하시겠습니까?');
	if(result == true){
		location.href='announceFileDownload?num=${announcement.num }'
	}
}

function deleteCheck(){
	result = confirm('공지사항을 삭제하겠습니까?');
	if(result == true){
		location.href='deleteAnnouncementProc?num=${announcement.num }'
	}
}
</script>
</head>
<body>
<c:import url="/header"></c:import>
<div id="wrap">
	<div class="viewAnnounce">
		<h2>${announcement.title }</h2>
		<p class="settingBox">
			<span class="material-symbols-outlined">account_circle</span>
			<span>${announcement.writer }</span>
			<a href="#" id="settings"><span class="material-symbols-sharp">settings</span></a>
			
		</p>
		<ul class="clickSetting">
			<li onclick="location.href='modifyAnnouncement?num=${announcement.num }'">수정</li>
			<li>|</li>
			<li onclick="deleteCheck()">삭제</li>
		</ul>
		<p>${announcement.writeDate } ${announcement.writeTime }</p>
		<p>조회 ${announcement.hits }</p>
		<hr>
		<p class="contentArea">${announcement.content }</p>
		<hr>
		<div class="fileDiv">
			<c:choose>
			<c:when test="${empty announcement.files }">
					<p></p>
				</c:when>
				<c:otherwise>
					<%-- <p onclick="location.href='announceFileDownload?num=${announcement.num }'">파일 다운로드 > <span>${announcement.files }</span></p> --%>
					<p onclick="fileDownload()">파일 다운로드 > <span>${announcement.files }</span></p>
				</c:otherwise>
			</c:choose>
		</div>
		
		<script>
        function clickHeart() {
        	 var heartIcon = document.querySelector('.heart');
        	    heartIcon.classList.toggle('heart-filled');
        	
            // 좋아요를 누른 공지사항의 num과 사용자의 id를 가져온다고 가정합니다.
            var announcementNum = ${announcement.num };
	
            /*
            xhr = new XMLHttpRequest();
       		xhr.open('post', 'clickHeart');
       		var sendData = announcementNum;
       		xhr.send(sendData);
       		xhr.onreadystatechange = resProc;
            */
            /*
            var xhr = new XMLHttpRequest();
            xhr.open('post', 'clickHeart');
            xhr.setRequestHeader('Content-Type', 'application/json'); // JSON 형식으로 데이터 전송을 설정합니다.
            var sendData = JSON.stringify({ announcementNum: announcementNum }); // 데이터를 JSON 형식으로 변환합니다.
            xhr.send(sendData);
            xhr.onreadystatechange = resProc; 
            */
            /*
            var xhr = new XMLHttpRequest();
            xhr.open('post', 'clickHeart');
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded'); // form 데이터 전송을 설정합니다.
            var sendData = 'announcementNum=' + encodeURIComponent(announcementNum); // 데이터를 인코딩하여 전송합니다.
            xhr.send(sendData);
            xhr.onreadystatechange = resProc;
            */
            var formData = new FormData();
            formData.append('announcement_num', announcementNum);

            var xhr = new XMLHttpRequest();
            xhr.open('post', 'clickHeart');
            xhr.send(formData);
            xhr.onreadystatechange = resProc;
        }
        
       	var xhr;
       	function resProc(){
       		if(xhr.readyState !== 4) 
       			return;
       	
       		if(xhr.status === 200) {
       			var heartNum = document.getElementById('heartNum');
       			heartNum.innerHTML = xhr.responseText;
       		} else {
       			console.log('에러: ' + xhr.status); // 요청 도중 에러 발생
       		}
       	}
        
    </script>
		
		<ul class="heartAndreply">
			<li><span class="heart" onclick="clickHeart()">&#10084;</span></li>
			<li id="heartNum">${announcement.likes }</li>
			<li><span class="material-symbols-sharp">chat</span></li>
			<li>댓글수</li>
		</ul>
		 
		<div class="h50"></div>
		<div class="announceReply">
			<h2><span class="material-symbols-sharp">chat</span> 댓글</h2>
			<table class="announceReplyTable">
				<tr class="announceWriteReply">
					<td>
						<input type="text" placeholder="댓글 작성(최대 100자)"/>
						<button>작성</button>
					</td>
				</tr>
				<tr>
					<td>
						<p>댓글 내용</p>
						<p>작성자 작성일시</p>
						<span class="material-symbols-sharp">more_vert</span>
					</td>
				</tr>
				<tr>
					<td>
						<p>댓글 내용</p>
						<p>작성자 작성일시</p>
						<span class="material-symbols-sharp">more_vert</span>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="viewListBtn">
		<button  onclick="location.href='announcement'">목록</button>
	</div>
</div>
<c:import url="/footer"></c:import>
</body>
</html>