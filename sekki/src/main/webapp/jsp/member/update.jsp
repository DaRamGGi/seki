<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/header" />
<style>
.MAPS {
	border-right: 1px solid #ccc;
	flex: 1;
	display: flex;
	flex-direction: column;
	align-items: center; /* 세로 방향 가운데 정렬 */
	margin: 10px; /* 각 섹션 간격을 위한 마진 */
}

.MAPS h2 {
	padding: 10px;
	border-bottom: 1px solid #ccc;
}
</style>
<div align="center" class="sub_div">
	<h1>회원 수정</h1>

	<table>
		<tr>
			<td>
				<form action="updateProc" method="post" id="f">
					<input type="text" value="${sessionScope.id }" id="id">
					(*필수 항목) <br> <input type="password" name="pw"
						placeholder="비밀번호" id="pw"><br> <input
						type="password" name="confirm" placeholder="비밀번호 확인 " id="confirm"
						onchange="pwCheck()"> <label id="label"></label><br>
					<input type="text" name="userName" id="userName"
						value="${sessionScope.userName }"><br> <input
						type="text" name="address" value="${sessionScope.address }"><br>
					<input type="text" name="mobile" value="${sessionScope.mobile }"><br>
					<input type="file" name="profilePicture" id="profilePicture"><br>

					<input type="button" value="회원수정" onclick="allCheck()" class="btn">
					<input type="button" value="취소" onclick="location.href='mypage'"
						class="btn"><br>
				</form>
			</td>
		</tr>
	</table>

	<div class="MAPS">
		<div
			style="display: flex; flex-direction: column; align-items: center;">
			<h2 style="text-align: center;">KAKAO 지도</h2>
			<input type="text" id="searchInput" placeholder="검색할 지역을 입력하세요">
			<button id="searchButton">검색</button>
			<div id="map"
				style="width: 400px; height: 400px; margin-right: 20px; margin-top: 20px;"></div>

		</div>
	</div>
</div>

<script type="text/javascript"
	src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=47ccec590c1f3b7c282f048e546230c2&autoload=false"></script>
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=47ccec590c1f3b7c282f048e546230c2&libraries=services,clusterer,drawing"></script>
<script>
	var id = '<%=session.getAttribute("id")%>';
	var address = '<%=session.getAttribute("address")%>';
	var map;
	var markers = [];
	  // 페이지 로드 시 사용자 주소를 기반으로 지도를 표시
	     document.addEventListener('DOMContentLoaded', function() {
	    showUserAddressOnMap();
	    
	    
	 // 검색 버튼 클릭 이벤트 처리
	    document.getElementById('searchButton').addEventListener('click', function() {
	      searchPlace();  
	  });
	// 사용자 주소로부터 위도와 경도 가지고오기
  });  
	
	
	
	
	
	function showUserAddressOnMap() {
	     if (address) {
	      var geocoder = new kakao.maps.services.Geocoder();
	      geocoder.addressSearch(address, function(result, status) {
	        if (status === kakao.maps.services.Status.OK) {
	          var latitude = result[0].y;
	          var longitude = result[0].x;
	          var mapContainer = document.getElementById('map');
	          var options = {
	            center: new kakao.maps.LatLng(latitude, longitude),
	            level: 3
	          };
	          map = new kakao.maps.Map(mapContainer, options);
	          
	      	   // 사용자 주소를 기반으로 주변 마트 검색 및 마커 표시
              showMartLocationsAroundUser(latitude, longitude);
	        }
	      });
	    }
	  }
	function showMartLocationsAroundUser(latitude, longitude) {
		// 이전에 표시된 마커들을 모두 제거 (초기화)
		removeAllMarkers();
		
		// 사용자 위치를 중심으로 주변 마트 검색을 위한 장소 검색 서비스 객체 생성
		  var placesService = new kakao.maps.services.Places();

		  // 주변 마트 검색 요청을 위한 장소 검색 객체 생성
		  var request = {
		    location: new kakao.maps.LatLng(latitude, longitude), // 사용자 위치
		    radius: 4000, // 검색 반경 (미터 단위, 여기서는 4km)
		    keyword: '마트' // 검색 키워드 (이 부분은 마트에 해당하는 키워드로 수정)
		  };

		  // 주변 마트 검색 요청
		  placesService.keywordSearch(request, function (result, status) {
		    if (status === kakao.maps.services.Status.OK) {
		      // 검색 결과가 있을 경우, 얻어온 마트 정보를 지도에 표시
		      for (var i = 0; i < result.length; i++) {
		        var place = result[i];
		        var marker = new kakao.maps.Marker({
		          position: new kakao.maps.LatLng(place.y, place.x), // 마트 위치
		          map: map // 표시할 지도 객체
		        });
		        markers.push(marker);
		      }
		    }
		  });
		}
	
	// 검색 폼의 입력값을 이용하여 장소 검색
	
	  function searchPlace() {
	        // 이전에 표시된 마커들을 모두 제거
	        removeAllMarkers();
		  
	   var keyword = document.getElementById('searchInput').value;
	    			

	    // 장소 검색 서비스 객체 생성
	    var placesService = new kakao.maps.services.Places();

	    // 장소 검색 요청
	    placesService.keywordSearch(keyword, function(result, status) {
	      if (status === kakao.maps.services.Status.OK) {
	        // 검색 결과가 있을 경우, 얻어온 장소의 위치를 지도에 표시
	        var place = result[0];
	        var latitude = place.y;
	        var longitude = place.x;
	        

	        // 검색된 장소로 지도 이동
	        map.setCenter(new kakao.maps.LatLng(latitude, longitude));


	        // 검색된 장소에 마커 표시
	        var marker = new kakao.maps.Marker({
	          position: new kakao.maps.LatLng(latitude, longitude),
	          map: map
	        });
	        markers.push(marker); // 마커 배열에 추가
	      }
	    });
	  }
	
	// 이전에 표시된 마커들을 모두 제거하는 함수
	  
	function removeAllMarkers() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
	}
</script>
<c:import url="/footer" />