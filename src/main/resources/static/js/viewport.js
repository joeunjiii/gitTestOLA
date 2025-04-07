// JavaScript 코드: 중복 선택 가능하게 조정
document.querySelectorAll('.ott input[type="radio"]').forEach(radio => {
    radio.addEventListener('click', function () {
        if (this.checked) {
            if (this.dataset.selected === "true") {
                this.checked = false; // 다시 클릭 시 해제
                this.setAttribute("name", "ott");
                this.dataset.selected = "false";
            } else {
                this.removeAttribute("name"); // 그룹 해제해서 중복 허용
                this.dataset.selected = "true";
            }
        } else {
            this.setAttribute("name", "ott");
            this.dataset.selected = "false";
        }
    });
});
  