document.addEventListener("DOMContentLoaded", function () {
    const ottRadios = document.querySelectorAll('.ott input[type="radio"]');
    const goMainBtn = document.getElementById("goMainBtn");

    goMainBtn.disabled = true;

    ottRadios.forEach(radio => {
        radio.addEventListener('click', function () {
            if (this.dataset.selected === "true") {
                this.dataset.selected = "false";
                this.setAttribute("name", "ott");
                this.checked = false;
            } else {
                this.dataset.selected = "true";
                this.removeAttribute("name"); // 다중 선택 허용
            }

            const anySelected = Array.from(ottRadios).some(r => r.dataset.selected === "true");
            goMainBtn.disabled = !anySelected;
        });
    });

    goMainBtn.addEventListener("click", function () {
        const selectedOtt = Array.from(ottRadios)
            .filter(r => r.dataset.selected === "true")
            .map(r => r.value);

        const selectedYear = document.querySelector('input[name="latestYear"]:checked');

        if (!selectedOtt.length || !selectedYear) {
            alert("OTT와 최신년도 선택을 모두 완료해주세요.");
            return;
        }

        const requestData = {
            ottPlatform: selectedOtt, // 배열 그대로 보냄
            latestYear: selectedYear.value === "1" // 문자열 "1" → boolean true
        };

        console.log("서버로 전송될 데이터:", requestData);

        fetch("/genre/save-ott", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "include",
            body: JSON.stringify(requestData)
        })
            .then(res => {
                if (res.ok) {
                    window.location.href = "/main";
                } else {
                    alert("저장 실패! 다시 시도해주세요.");
                }
            })
            .catch(err => {
                console.error("요청 중 에러:", err);
                alert("네트워크 오류가 발생했습니다.");
            });
    });
});
