
// HTML 코드 삽입 시점인 DOMContentLoaded에서 스크립트를 실행
document.addEventListener('DOMContentLoaded', function() {
	// 메인 요리 이미지 관련 코드
	const mainPhotoHolder = document.getElementById('mainPhotoHolder');
	const fileInput = document.getElementById('fileInput');
	if (mainPhotoHolder && fileInput) {
		mainPhotoHolder.addEventListener('click', function() {
			fileInput.click();
		});

		fileInput.addEventListener('change', function() {
			const file = fileInput.files[0];
			const reader = new FileReader();

			reader.onload = function(event) {
				mainPhotoHolder.src = event.target.result;
			};

			if (file) {
				reader.readAsDataURL(file);
			}
		});
	}

	// 재료 추가하기 관련 코드
	const materialContainer = document.getElementById('materialContainer');
	const materialDataArray = []; // 새로 추가: 재료 데이터를 저장할 배열

	function addMaterialButtonClick() {
		const newMaterialDiv = document.createElement('div');
		newMaterialDiv.className = 'right_material';
		newMaterialDiv.innerHTML = `
          <div class="right_boxs">
            <input type="text" name="materialname" class="form-control materials_css" placeholder="예) 돼지고기">
            <input type="text" name="materialamount" class="form-control materials_css" placeholder="예) 100g">
            <button type="button" class="removeMaterialButton">x</button>
          </div>
        `;
		materialContainer.appendChild(newMaterialDiv);
	}

	const addMaterialButton = document.getElementById('addMaterialButton');
	if (addMaterialButton) {
		addMaterialButton.addEventListener('click', addMaterialButtonClick);
	}

	materialContainer.addEventListener('click', function(event) {
		if (event.target.classList.contains('removeMaterialButton')) {
			const materialDiv = event.target.closest('.right_material');
			if (materialDiv) {
				materialDiv.remove();
			}
		}
	});

	// 새로 추가: 폼 제출 시 재료 데이터를 서버로 전송
	const materialForm = document.getElementById('materialForm');
	if (materialForm) {
		materialForm.addEventListener('submit', function(event) {
			event.preventDefault();
			// 서버로 전송하거나 필요한 처리를 여기에 추가합니다.
			console.log('재료 데이터:', materialDataArray);
		});
	}
	
function previewImage(input, stepIndex) {
  const file = input.files[0];
  if (!file) return;

  const reader = new FileReader();

  reader.onload = function(e) {
    const imageElement = document.getElementById(`stepPhotoHolder_STEP_${stepIndex}`);
    if (imageElement) {
      imageElement.src = e.target.result;
    }
  };

  reader.readAsDataURL(file);
}

  const stepPlusBtn = document.getElementById('step_plus_btn');
  stepPlusBtn.addEventListener('click', function() {
    const stepCount = document.getElementsByClassName('cok_step').length + 1;
    const newStepDiv = document.createElement('div');
    newStepDiv.className = 'cok_step';
    newStepDiv.innerHTML = `
      <p class="cok_step_p">Step${stepCount}</p>
      <div id="cok_step_box">
        <textarea name="step_text" id="step_text_STEP_${stepCount}" class="form-control step_cont step_text_STEP_css" placeholder="예) 소고기 맛나게 구워드세요"></textarea>
      </div>
      <div id="divStepPhotoBox_STEP_${stepCount}" is_over="0">
        <label for="step_photoholder_STEP_${stepCount}" class="step_photoLabel">
          <img id="stepPhotoHolder_STEP_${stepCount}" class="stepPhotoHolder_STEP_css" src="https://recipe1.ezmember.co.kr/img/pic_none2.gif">
        </label>
        <input type="file" name="step_photoholder" id="step_photoholder_STEP_${stepCount}" class="step_photoholder" accept="image/*">
      </div>
    `;
    const cokStepElements = document.getElementsByClassName('cok_step');
    const lastCokStepElement = cokStepElements[cokStepElements.length - 1];
    lastCokStepElement.insertAdjacentElement('afterend', newStepDiv);

    // 추가된 스텝의 이미지 입력란에 이벤트 등록
    const newStepInput = document.getElementById(`step_photoholder_STEP_${stepCount}`);
    newStepInput.addEventListener('change', function() {
      previewImage(this, stepCount);
    });
  });

  // 이미지 선택 시 미리보기 기능
  const stepPhotoInputs = document.querySelectorAll('.step_photoholder');
  stepPhotoInputs.forEach((input, index) => {
    input.addEventListener('change', function() {
      previewImage(this, index + 1);
    });
  });
});

//욧기ㅏ부터
function openFileSelector(stepIndex) {
  const fileInput = document.getElementById(`step_photoholder_STEP_${stepIndex}`);

  if (!fileInput.value) { // 이미 파일 선택이 되어 있는 경우에는 무시
    fileInput.click();
  }
}

// 이미지 선택 시 미리보기 기능은 그대로 유지합니다.
const stepPhotoInputs = document.querySelectorAll('.step_photoholder');
stepPhotoInputs.forEach((input, index) => {
  const stepIndex = index + 1; // stepIndex 계산
  input.addEventListener('change', function() {
    previewImage2(this, stepIndex); // stepIndex를 전달하여 함수 호출
  });
});

//업데이트 삭제
function deleteRecipe(re_no) {
	result = confirm('정말로 레시피를 삭제하겠습니까?');
	if (result == true) {
		location.href = 'deleteRecipe?re_no=' + re_no;
	}
}
