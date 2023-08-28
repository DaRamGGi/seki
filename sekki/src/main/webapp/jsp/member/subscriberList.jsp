<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/header" />
<Style>

.sub_div{
	margin-bottom: 150px;
}


h1 {
	text-align:left;
    padding-left:40px; 
}
h2{
	text-align:right;
	padding-right:40px;
	padding-bottom:60px;
}

.channel-table {
    width: 100%;
    border-collapse: collapse;
}

.channel-table th, .channel-table td {
    border: 1px solid #ddd;
    padding: 10px;
    text-align: center;
}

.column-id {
    width: 20%;
}

.column-date {
    width: 30%;
}

.column-subscribers {
    width: 20%;
}

.column-subscribe {
    width: 30%;
}

.subscription-btn {
    width: 100px;
    height: 40px;
    background-color: #fff;
    color: black;
    border: none;
    cursor: pointer;
}


</Style>

	<div align="center" class="sub_div">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<td class="sub_td">
	<h1>구독자 관리</h1>
	<h2>구독자 수 : ${subscriber.subscriber_Count}명</h2>
	</td>
		<c:choose>
			<c:when test="${empty subscriberList }">
				<h1> 구독한 유저가 존재하지 않습니다. </h1>
			</c:when>
			<c:otherwise>
    	<table class="channel-table" border="1">
        <thead>
            <tr>
                <th class="column-id">아이디</th>
		        <th class="column-date">날짜</th>
		        <th class="column-subscribers">구독자 수</th>
		        <th class="column-subscribe">구독하기</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="subscriber" items="${subscriberList}" >
                <tr align="center" >
                    <td onclick="location.href='userInfo?id=${member.id }&currentPage=${currentPage }'">${subscriber.id}</td>
                    <td>${subscriber.registration_Date}</td>
                    <td>${subscriber.subscriber_Count}명</td>
                    <td>
              	<c:choose>
			     <c:when test="${subscriber.is_Subscribed}">
        <!-- 구독 중인 경우 -->
		        <span class="subscription-btn" data-subscriber-id="${subscriber.id}" data-action="unsubscribe"
		              onclick="toggleSubscription(this)">&#x2764;</span>
			    </c:when>
			    <c:otherwise>
		        <!-- 구독하지 않은 경우 -->
		        <span class="subscription-btn" data-subscriber-id="${subscriber.id}" data-action="subscribe"
		              onclick="toggleSubscription(this)">&#x2661;</span>
		   	 	</c:otherwise>
			    </c:choose>
				</td>
                </tr>
            	</c:forEach>
    		</table>
            	
            	<div> ${result}	</div>

				<form action="subscriberList">
					<select name="select" >
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
    
<script>
function toggleSubscription(button) {
    var subscriberId = button.getAttribute("data-subscriber-id");
    var action = button.getAttribute("data-action");
    
    // 이 부분에서 구독 상태를 서버로 보내고 처리하는 로직을 추가해야 합니다.
    // AJAX를 사용하여 서버로 요청을 보내고, 서버에서 구독 상태를 변경합니다.
    // 예를 들어, 구독 상태를 서버에 전달하고 응답을 받아와서 버튼 모양을 변경할 수 있습니다.
    
    // 이후에 서버 응답에 따라 아래와 같이 버튼을 변경합니다.
    if (action === "subscribe") {
        button.innerHTML = "&#x2764;";
        button.setAttribute("data-action", "unsubscribe");
    } else if (action === "unsubscribe") {
        button.innerHTML = "&#x2661;";
        button.setAttribute("data-action", "subscribe");
    }
}
$(document).ready(function() {
    $('span.subscription-btn').click(function() {
        var subscriberId = $(this).data('subscriber-id');
        var action = $(this).data('action');
        
        var $btn = $(this); // 버튼 요소를 변수로 저장
        
        $.ajax({
            url: '/updateSubscription', // 실제 업데이트 처리 URL로 수정
            method: 'POST',
            data: {
                subscriberId: subscriberId,
                action: action
            },
            success: function(response) {
                if (response === 'success') {
                    // 서버에서 업데이트 성공 시, 클라이언트 UI를 업데이트합니다.
                    if (action === 'subscribe') {
                        $btn.html('구독취소'); // 버튼 텍스트 변경
                        $btn.removeClass('subscription-btn-subscribe');
                        $btn.addClass('subscription-btn-unsubscribe');
                    } else if (action === 'unsubscribe') {
                        $btn.html('구독하기'); // 버튼 텍스트 변경
                        $btn.removeClass('subscription-btn-unsubscribe');
                        $btn.addClass('subscription-btn-subscribe');
                    }
                }
            },
            error: function(xhr, status, error) {
                // 에러 처리 로직을 추가해야 합니다.
            }
        });
    });
});
</script>


<c:import url="/footer" />