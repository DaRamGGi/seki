
//----------------메인 요리 이미지-----------------
// 이미지를 클릭하면 파일 선택창 열기
function browseMainFile() {
  const fileInput = document.getElementById('fileInput');
  fileInput.click();
}

// 파일 선택창에서 이미지 선택 시
function showSelectedImage() {
  const file = document.getElementById('fileInput').files[0];
  const mainPhotoHolder = document.getElementById('mainPhotoHolder');

  const reader = new FileReader();

  reader.onload = function (event) {
    // 이미지 데이터를 img 요소에 출력
    mainPhotoHolder.src = event.target.result;
  };

  if (file) {
    reader.readAsDataURL(file);
  }
}
//----------------메인 요리 이미지-----------------
//----------------재료 추가하기-------------------
document.addEventListener('DOMContentLoaded', function () {
  const materialContainer = document.getElementById('materialContainer');
  const materialDataArray = []; // 새로 추가: 재료 데이터를 저장할 배열

  function addMaterialButtonClick() {
    const newMaterialDiv = document.createElement('div');
    newMaterialDiv.className = 'right_material';
    newMaterialDiv.innerHTML = `
      <div class="right_boxs">
        <input type="text" name="materialName" class="form-control materials_css" placeholder="예) 돼지고기">
        <input type="text" name="materialAmount" class="form-control materials_css" placeholder="예) 100g">
        <button type="button" class="removeMaterialButton">x</button>
      </div>
    `;
    materialContainer.appendChild(newMaterialDiv);
  }

  const addMaterialButton = document.getElementById('addMaterialButton');
  if (addMaterialButton) {
    addMaterialButton.addEventListener('click', addMaterialButtonClick);
  }

  materialContainer.addEventListener('click', function (event) {
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
    materialForm.addEventListener('submit', function (event) {
      event.preventDefault();
      // 서버로 전송하거나 필요한 처리를 여기에 추가합니다.
      console.log('재료 데이터:', materialDataArray);
    });
  }
});


//----------------재료 추가하기-------------------
//----------------stpe 추가하기------------------
document.addEventListener('DOMContentLoaded', function () {
    const stepPlusBtn = document.getElementById('step_plus_btn');
    stepPlusBtn.addEventListener('click', function () {
        const stepCount = document.getElementsByClassName('cok_step').length + 1;
        const newStepDiv = document.createElement('div');
        newStepDiv.className = 'cok_step';
        newStepDiv.innerHTML = `
            <p class="cok_step_p">Step${stepCount}</p>
            <div id="cok_step_box">
                <textarea name="step_text[]" id="step_text_STEP_${stepCount}" class="form-control step_cont step_text_STEP_css" placeholder="예) 소고기 맛나게 구워드세요"></textarea>
            </div>
            <div id="divStepPhotoBox_STEP_${stepCount}" is_over="0">
                <img id="stepPhotoHolder_STEP_${stepCount}" class="stepPhotoHolder_STEP_css" src="https://recipe1.ezmember.co.kr/img/pic_none2.gif">
            </div>
        `;
        const cokStepElements = document.getElementsByClassName('cok_step');
        const lastCokStepElement = cokStepElements[cokStepElements.length - 1];
        lastCokStepElement.insertAdjacentElement('afterend', newStepDiv);
    });
});

//----------------stpe 추가하기------------------