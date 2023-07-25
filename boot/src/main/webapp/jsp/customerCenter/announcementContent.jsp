<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		<div>
			<h2>${announcement.title }</h2>
			<p>${announcement.writer }</p>
		</div>
		<div>
			<p>${announcement.writeDate }</p>
			<p>${announcement.hits }</p>
		</div>
		<div>
			<p>${announcement.content }</p>
		</div>
	</div>
<c:import url="/footer"/>

</body>
</html>