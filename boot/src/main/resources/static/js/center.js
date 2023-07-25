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
