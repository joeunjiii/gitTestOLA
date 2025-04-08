document.addEventListener("DOMContentLoaded", function () {
    const ottRadios = document.querySelectorAll('.ott input[type="radio"]');
    const goMainBtn = document.getElementById("goMainBtn");

    // 버튼 기본 비활성화
    goMainBtn.disabled = true;

    ottRadios.forEach(radio => {
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

            // 하나라도 선택되어 있으면 버튼 활성화
            const anySelected = Array.from(ottRadios).some(r => r.dataset.selected === "true");
            goMainBtn.disabled = !anySelected;

            // 선택 완료 클릭 시 /main 이동
            if (anySelected) {
                goMainBtn.onclick = () => {
                    const selectedOtt = Array.from(ottRadios)
                        .filter(r => r.dataset.selected === "true")
                        .map(r => r.value);

                    localStorage.setItem("selectedOtt", JSON.stringify(selectedOtt));


                    window.location.href = "/main";
                };
            }
        });
    });
});
