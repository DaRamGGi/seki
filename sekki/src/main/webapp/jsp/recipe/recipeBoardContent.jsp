<%@ page import="java.io.OutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/header" />
<c:url var="context" value="/" />
<link href="css/recipes.css" rel="stylesheet" />
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<script src="/js/recipe.js"></script>
<div id="full">
	<div id="width1200">
		<%--메인 인포 --%>
		<div id="main_con_info">
			<div class="main_photo">
				<img src="">
			</div>
			<div class="user_inffo">
				<img src="${recipeCon.mainphoto }" class="main_img_con">
				<div id="user_info_con">
					<img src="${recipeCon.profile}" class="user_profile_con">
					<p id="user_info_con_text">${recipeCon.id}</p>
				</div>
			</div>

			<div class="main_content">
				<h1>[${recipeCon.category}]${recipeCon.title}</h1>
				<p>${recipeCon.content}</p>
				<ul id="cook_info">
					<li><img src="image/icon_man.png"> ${recipeCon.cuisine }
					</li>
					<li><img src="image/icon_star.png"> ${recipeCon.times }
					</li>
					<li><img src="image/icon_time2.png"> ${recipeCon.degree }
					</li>

				</ul>
			</div>
		</div>
		<%--메인 인포 --%>

		<%--메인 재료 --%>
		<div class="cont_box pd_l_60 content_borad">
			<div id="materialss">
				<h3>재료</h3>
				<ul class="mate_con">
					<c:forEach var="reciMa" items="${reciMa}">
						<li class="mate_con_list"><span class="mate_con_name">${reciMa.materialname}</span><span
							class="mate_con_alam">${reciMa.materialamount}</span></li>


					</c:forEach>
				</ul>
			</div>
		</div>
		<%--메인 재료 --%>
		<%--step --%>
		<div class="cont_box pd_l_60 content_borad">
			<div id="stepss">
				<h1>조리 순서</h1>
				<c:forEach var="reciStepList" items="${reciStepList}">
					<ul>
						<li class="step_con_list">
							<div class="step_index">${loop.index + 1}</div> <span
							class="step_texts">${reciStepList.step_text}</span> <img
							class="step_con_img" src="${reciStepList.step_photoholder }">
						</li>
					</ul>
				</c:forEach>
			</div>
		</div>
		<%--step --%>

		<%-- 별점 --%>
		<div class="cont_box pd_l_60 content_borad">
			<h2>댓글</h2>
			<div class="coments">
				<div class="coment_left">
					<div class="coment_profile_div">
						<img class="coment_profile" src="${recipeCon.profile }">
					</div>
				</div>
				<div class="coment_right">
					<div class="coment_inner">
						<div>
							<b>${recipeCon.id}</b> <span class="coment_time">${recipeCon.written_time}</span>
							<span class="coment_star"><img class="coment_star_in"
								src="image/star_icon.png"><img class="coment_star_in"
								src="image/star_icon.png"><img class="coment_star_in"
								src="image/star_icon.png"><img class="coment_star_in"
								src="image/star_icon.png"><img class="coment_star_in"
								src="image/star_icon.png"> </span>
						</div>
						<div class="coment_content">
							<p>${recipeCon.content }</p>
						</div>
					</div>
				</div>

			</div>
			<div class="comnet_written">
				<form action="commentProc" method="post">
					<!-- 서버_처리_URL은 실제 서버로 요청을 보낼 URL로 변경해야 합니다. -->
					<textarea rows="5" cols="40" name="comment_content"
						placeholder="기본값은 여기에 적어줍니다."></textarea>
					<button type="submit">등록</button>
				</form>
			</div>
		</div>

		<%-- 별점 --%>

	</div>
</div>

<c:import url="/footer" />