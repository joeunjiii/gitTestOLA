document.addEventListener("DOMContentLoaded", function () {
    const stars = document.querySelectorAll(".star");
    const ratingInput = document.getElementById("postRating");

    stars.forEach(star => {
        star.addEventListener("click", () => {
            const ratingValue = star.getAttribute("data-value");
            ratingInput.value = ratingValue;

            // 모든 별 초기화
            stars.forEach(s => s.style.color = "#ccc");

            // 선택한 별부터 이전 별까지 노란색 적용
            for (let i = 0; i < ratingValue; i++) {
                stars[i].style.color = "gold";
            }
        });
    });
});

function saveReviewAndMove() {
    const postTitle = document.getElementById('review-text').value;
    const rating = document.getElementById('postRating').value;

    // localStorage에 저장 -> submit 처리는 form이 알아서 처리하게
    localStorage.setItem('postTitle', postTitle);
    localStorage.setItem('rating', rating);

    // 다음 페이지로 이동
    window.location.href = "/review_photo";
}