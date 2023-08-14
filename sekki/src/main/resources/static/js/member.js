/*로그인=============================================================================*/
function loginCheck(){
	let id = document.getElementById('id');
	let pw = document.getElementById('pw');

	if(id.value == ""){
		alert('아이디는 필수 항목입니다.');
	}else if(pw.value == ""){
		alert('비밀번호는 필수 항목입니다.');
	}else{
		var f = document.getElementById('f');
		f.submit();
	}
}
/*약관동의&본인인증=============================================================================*/
document.addEventListener("DOMContentLoaded", function() {
    var allAgreeCheckbox = document.getElementById("allAgree");
    var otherCheckboxes = document.querySelectorAll(".agreeConditionTable tbody input[type='checkbox']");

    allAgreeCheckbox.addEventListener("change", function() {
        var isChecked = allAgreeCheckbox.checked;

        otherCheckboxes.forEach(function(checkbox) {
            checkbox.checked = isChecked;
        });
    });

    otherCheckboxes.forEach(function(checkbox) {
        checkbox.addEventListener("change", function() {
            if (this.checked && areAllCheckboxesChecked(otherCheckboxes)) {
                allAgreeCheckbox.checked = true;
            } else {
                allAgreeCheckbox.checked = false;
            }
        });
    });

    function areAllCheckboxesChecked(checkboxes) {
        for (var i = 0; i < checkboxes.length; i++) {
            if (!checkboxes[i].checked) {
                return false;
            }
        }
        return true;
    }
});

/*회원가입=============================================================================*/
/*파일 선택시 input tag에 파일명 표시 (register.jsp에 제이쿼리 추가)*/
document.addEventListener("DOMContentLoaded", function() {
	$("#profilePicture").on('change', function() {
		var fileName = $(this).val().split('\\').pop();
		$(".upload-name").val(fileName);
	});
});

function execDaumPostcode() {
	new daum.Postcode({
		oncomplete: function(data) {
			if (data.userSelectedType === 'R') {
				document.getElementById('address').value = data.roadAddress;
			} else {
				document.getElementById('address').value = data.jibunAddress;
			}
			document.getElementById('postcode').value = data.zonecode;
		}
	}).open();
}

function regIdCheck() {
	xhr = new XMLHttpRequest();
	xhr.open('post', 'regIdCheck');
	var sendData = document.getElementById('id').value;
	xhr.send(sendData);
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var regIdLabel = document.getElementById('regIdLabel');
			regIdLabel.innerHTML = xhr.responseText;
		};
	}
}

function regPwCheck() {
	xhr = new XMLHttpRequest();
	xhr.open('post', 'regPwCheck');
	var sendData = document.getElementById('pw').value;
	xhr.send(sendData);
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var regPwLabel = document.getElementById('regPwLabel');
			regPwLabel.innerHTML = xhr.responseText;
		};
	}
}

function regConfirmCheck(){
	let pw = document.getElementById('pw');
	confirm = document.getElementById('confirm');
	regConfirmLabel = document.getElementById('regConfirmLabel');
	 if(pw.value == confirm.value){
		 regConfirmLabel.innerHTML = ''
	 }else{
		 regConfirmLabel.innerHTML = '* 비밀번호를 확인하세요.'
	 }
}

function regMobileCheck() {
	xhr = new XMLHttpRequest();
	xhr.open('post', 'regMobileCheck');
	var sendData = document.getElementById('mobile').value;
	xhr.send(sendData);
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var regMobileLabel = document.getElementById('regMobileLabel');
			regMobileLabel.innerHTML = xhr.responseText;
		};
	}
}

function allCheck(){
	let id = document.getElementById('id');
	let pw = document.getElementById('pw');
	confirm = document.getElementById('confirm');
	userName = document.getElementById('userName');

	if(id.value == ""){
		alert('아이디를 입력하세요.');
	}else if(pw.value == ""){
		alert('비밀번호를 입력하세요.');
	}else if(confirm.value == ""){
		alert('비밀번호를 확인하세요.');
	}else if(userName.value == ""){
		alert('이름/닉네임(을)를 입력하세요.');
	}else{
		var f = document.getElementById('f');
		f.submit();
	}
}

