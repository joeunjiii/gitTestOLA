document.addEventListener("DOMContentLoaded", function () {
    const searchInput = document.getElementById("ott-search");
    const contentSelection = document.querySelector(".content-selection");

    // ✅ 검색 input 이벤트
    searchInput.addEventListener("input", function () {
        const keyword = searchInput.value.trim();

        if (keyword === "") {
            contentSelection.innerHTML = "<p>작품명을 검색해 주세요.</p>";
            return;
        }

        fetch(`/posts/search?keyword=${encodeURIComponent(keyword)}`)
            .then((response) => response.json())
            .then((data) => {
                contentSelection.innerHTML = ""; // 이전 검색 결과 초기화

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

                    //  선택된 콘텐츠 저장 (예: sessionStorage, localStorage, hidden input 등)
                    box.addEventListener("click", () => {
                        sessionStorage.setItem("selectedTitle", content.title);
                        sessionStorage.setItem("selectedPoster", content.posterImg);
                        sessionStorage.setItem("selectedContentId", content.id); // ✅ 이거 추가!

                        alert(`"${content.title}"을(를) 선택하셨습니다.`);

                        contentSelection.innerHTML = "";
                        contentSelection.appendChild(box);
                        box.classList.add("selected");
                    });


                    contentSelection.appendChild(box);
                });
            })
            .catch((error) => {
                console.error("검색 중 오류:", error);
                contentSelection.innerHTML = "<p>검색 오류가 발생했습니다.</p>";
            });
    });
});


