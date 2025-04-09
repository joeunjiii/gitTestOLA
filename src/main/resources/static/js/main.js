const navItems = document.querySelectorAll(".nav-item");
navItems.forEach((item) => {
    item.addEventListener("click", () => {
        navItems.forEach((el) => el.classList.remove("active"));
        item.classList.add("active");
    });
});
// 슬라이드 좌우 버튼 작동
const leftBtn = document.querySelector(".arrow.left");
const rightBtn = document.querySelector(".arrow.right");
const slideTrack = document.querySelector(".ott-slide-track");

const scrollAmount = 300;

leftBtn.addEventListener("click", () => {
    slideTrack.scrollBy({
        left: -scrollAmount,
        behavior: "smooth"
    });
});

rightBtn.addEventListener("click", () => {
    slideTrack.scrollBy({
        left: scrollAmount,
        behavior: "smooth"
    });
});
document.addEventListener("DOMContentLoaded", function () {
    const ottList = JSON.parse(localStorage.getItem("selectedOtt")) || [];

    if (ottList.length > 0) {
        fetch("/genre/save-ott", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ ottPlatform: ottList })
        }).then(res => {
            if (res.ok) {
                console.log("✅ OTT 저장 완료");
                localStorage.removeItem("selectedOtt"); // 저장 후 정리

                // ✅ 메인 페이지 새로고침 or 이동
                window.location.href = "/main"; // 또는 window.location.reload();
            } else {
                console.error("❌ OTT 저장 실패");
            }
        });
    }

    // 콘텐츠 이미지 클릭 시 선택된 제목 저장
    const slideItems = document.querySelectorAll(".slide-item");
    slideItems.forEach(item => {
        item.addEventListener("click", function (event) {
            event.preventDefault(); // a 태그 기본 동작 방지
            const selectedTitle = item.getAttribute("data-title");

            fetch("/genre/save-title", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ selectedTitle: selectedTitle })
            })
                .then(res => {
                    if (res.ok) {
                        console.log("✅ 콘텐츠 제목 저장 완료:", selectedTitle);
                        // 선택 후 페이지 새로고침 없이도 하단 추천 등 갱신 가능
                        // 필요 시 여기에 유사 콘텐츠 요청 트리거 추가 가능
                    } else {
                        console.error("❌ 콘텐츠 제목 저장 실패");
                    }
                });
        });
    });
});