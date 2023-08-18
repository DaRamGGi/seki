/*글 작성시*/
function nullCheck(){
	let title = document.getElementById('title');
	let content = document.getElementById('content');
	
	if(title.value == ""){
		alert('제목을 입력하세요.');
	}else if(content.value == ""){
		alert('내용을 입력하세요.');
	}else{
		var result = confirm("작성하시겠습니까?");
	}
	
	if(result){
		var f = document.getElementById('f');
		f.submit();
	}
}

/*톱니바퀴 클릭시 수정|삭제 나타남*/
document.addEventListener('DOMContentLoaded', function() {
  document.getElementById('settings').addEventListener('click', function(e) {
    e.preventDefault();
    var clickSetting = document.querySelector('.clickSetting');
    clickSetting.style.display = clickSetting.style.display === 'block' ? 'none' : 'block';
  });
});

/*function fileDownload(){
	result = confirm('파일을 다운로드 하시겠습니까?');
	if(result == true){
		location.href='announceFileDownload?num=${announcement.num }'
	}
}*/

/*파일 선택시 input tag에 파일명 표시 (html에 제이쿼리 사용을 위한 import 추가*/
document.addEventListener("DOMContentLoaded", function() {
    $("#file").on('change', function(){
        var fileName = $(this).val().split('\\').pop();
        $(".upload-name").val(fileName);
    });
});

function modifyCheck(announcementNum){
	
	console.log(announcementNum);
	
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
		location.href='modifyAnnouncementProc?num='+announcementNum;
	}
}

/*function deleteCheck(){
	result = confirm('공지사항을 삭제하겠습니까?');
	if(result == true){
		location.href='deleteAnnouncementProc?num='+announcementNum;
	}
}*/

function toggleHeart() {
    var heartIcon = document.querySelector('.heart');
    heartIcon.classList.toggle('heart-filled');
}
