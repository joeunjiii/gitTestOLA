// 좌우 스크롤 이동
function scrollLeft() {
    document.querySelector('.scroll-list')?.scrollBy({ left: -200, behavior: 'smooth' });
}
function scrollRight() {
    document.querySelector('.scroll-list')?.scrollBy({ left: 200, behavior: 'smooth' });
}

document.addEventListener("DOMContentLoaded", () => {
// 찜한 콘텐츠 클릭 시 상세 페이지 이동
document.querySelectorAll('.favorite-content-item').forEach(item => {
    item.addEventListener('click', () => {
        const title = item.querySelector('p')?.innerText || '작품';
        window.location.href = `/reviewDetail?title=${encodeURIComponent(title)}`;
    });
});

// 좋아요한 리뷰 클릭 시 상세 페이지 이동
document.querySelectorAll('.liked-review-item').forEach(item => {
    item.addEventListener('click', () => {
        const title = item.querySelector('p')?.innerText || '리뷰';
        window.location.href = `/reviewDetail?title=${encodeURIComponent(title)}`;
    });
});

// 내가 작성한 리뷰 클릭 시 상세 페이지 이동 (알림만 있었던 기존 코드 수정)
document.querySelectorAll('.review-item').forEach(item => {
    item.addEventListener('click', () => {
        const title = item.querySelector('p')?.innerText || '리뷰';
        window.location.href = `/reviewDetail?title=${encodeURIComponent(title)}`;
    });
});

});

// 선택되지 않은 OTT 숨기기
document.querySelectorAll('.selected-ott img').forEach(img => {
    if (img.dataset.selected !== 'true') {
        img.style.display = 'none';
    }
});
