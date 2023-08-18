<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/header" />


	<div align="center" class="sub_div">
		<h1>구독자 관리</h1>

		<c:choose>
			<c:when test="${empty subscriberList }">
				<h1> 구독한 유저가 존재하지 않습니다. </h1>
			</c:when>
			<c:otherwise>
    	<table border="1">
        <thead>
            <tr>
                <th>아이디</th>
                <th>날짜</th>
                <th>구독자 수</th>
                <th>구독하기</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="subscriber" items="${subscriberList}">
                <tr>
                    <td>${subscriber.id}</td>
                    <td>${subscriber.date}</td>
                    <td>${subscriber.count}명</td>
                    <td>
                        <c:choose>
                            <c:when test="${subscriber.member}">
                                <button>구독취소</button>
                            </c:when>
                            <c:otherwise>
                                <button>구독하기</button>
                            </c:otherwise>
                       	 </c:choose>
                    	</td>
                	</tr>
            	</c:forEach>
    		</table>
            	
            	<div> ${result}	</div>

				<form action="subscriberList">
					<select name="select">
						<option value="">전체</option>
						<option value="id">아이디</option>
					</select>
					<input type="text" name="search" />
					<input type="submit" value="검색" />
				</form>
        	</tbody>
      	</c:otherwise>
    </c:choose>
    </div>


<c:import url="/footer" />