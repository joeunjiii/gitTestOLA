document.addEventListener("DOMContentLoaded", function () {
    const stars = document.querySelectorAll(".star");

    stars.forEach(star => {
        star.addEventListener("click", () => {
            const ratingValue = star.getAttribute("data-value");

            // 모든 별 초기화
            stars.forEach(s => s.style.color = "#ccc");

            // 선택한 별부터 이전 별까지 노란색 적용
            for (let i = 0; i < ratingValue; i++) {
                stars[i].style.color = "gold";
            }
        });
    });
});