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

    const slideItems = document.querySelectorAll(".slide-item");
    slideItems.forEach(item => {
        item.addEventListener("click", function (event) {
            event.preventDefault();
            const selectedTitle = item.getAttribute("data-title");

            fetch("/genre/save-title", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ selectedTitle })
            })
                .then(res => {
                    if (res.ok) {
                        console.log("✅ 선택 제목 저장 완료:", selectedTitle);

                        // ✅ 선택 기반 추천 AJAX 요청
                        fetch(`/ai/selected?title=${encodeURIComponent(selectedTitle)}`)
                            .then(res => res.json())
                            .then(data => {
                                const track = document.querySelector(".ott-slide-track");
                                track.innerHTML = ""; // 기존 슬라이드 비움

                                if (data.length === 0) {
                                    track.innerHTML = "<p>추천 결과가 없습니다.</p>";
                                    return;
                                }

                                data.forEach(item => {
                                    const a = document.createElement("a");
                                    a.href = "#";
                                    a.className = "ott-card";

                                    const img = document.createElement("img");
                                    img.src = item.poster || "/images/no-image.png";
                                    img.alt = item.title;

                                    a.appendChild(img);
                                    track.appendChild(a);
                                });

                                console.log("✅ 추천 결과 동적 반영 완료");
                            });
                    } else {
                        console.error("❌ 제목 저장 실패");
                    }
                });
        });
    });

});
document.addEventListener("DOMContentLoaded", function () {
    const searchInput = document.getElementById("ott-search");
    const contentSelection = document.querySelector(".content-selection");

    searchInput.addEventListener("input", function () {
        const keyword = searchInput.value.trim();

        if (keyword === "") {
            contentSelection.innerHTML = "";
            contentSelection.style.display = "none";
            return;
        }

        fetch(`/posts/search?keyword=${encodeURIComponent(keyword)}`)
            .then((response) => response.json())
            .then((data) => {
                contentSelection.innerHTML = "";
                contentSelection.style.display = "block";  // ✅ 수정된 부분!

                if (data.length === 0) {
                    contentSelection.innerHTML = "<p>검색 결과가 없습니다.</p>";
                    return;
                }

                data.forEach((content) => {
                    const box = document.createElement("div");
                    box.className = "content-box";

                    const img = document.createElement("img");
                    img.src = content.posterImg || "/images/no-image.png";
                    img.alt = content.title;

                    const title = document.createElement("p");
                    title.className = "content-title";
                    title.textContent = content.title;

                    box.appendChild(img);
                    box.appendChild(title);

                    box.addEventListener("click", () => {
                        sessionStorage.setItem("selectedTitle", content.title);
                        sessionStorage.setItem("selectedPoster", content.posterImg);
                        alert(`"${content.title}"을(를) 선택하셨습니다.`);

                        contentSelection.innerHTML = "";
                        contentSelection.style.display = "none";
                    });

                    contentSelection.appendChild(box);
                });
            })
            .catch((error) => {
                console.error("검색 중 오류:", error);
                contentSelection.innerHTML = "<p>검색 오류가 발생했습니다.</p>";
                contentSelection.style.display = "block";
            });
    });

    // 검색창 외 클릭 시 결과 박스 숨김
    document.addEventListener("click", function (e) {
        const searchBox = document.querySelector(".search-box");

        if (!searchBox.contains(e.target)) {
            contentSelection.innerHTML = "";
            contentSelection.style.display = "none";
        }
    });
});
