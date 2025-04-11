// 좌우 스크롤 이동
function scrollLeft() {
    document.querySelector('.scroll-list')?.scrollBy({ left: -200, behavior: 'smooth' });
}

function scrollRight() {
    document.querySelector('.scroll-list')?.scrollBy({ left: 200, behavior: 'smooth' });
}

// 찜한 작품 클릭 알림
document.querySelectorAll('.scroll-item').forEach(item => {
    item.addEventListener('click', () => {
        const title = item.querySelector('img')?.alt || '작품';
        alert(`"${title}" 작품을 클릭했습니다!`);
    });
});

// 작성한 리뷰 클릭 알림
document.querySelectorAll('.review-item').forEach(item => {
    item.addEventListener('click', () => {
        const title = item.querySelector('p')?.innerText || '리뷰';
        alert(`"${title}" 리뷰를 클릭했습니다!`);
    });
});

// 선택되지 않은 OTT 숨기기
document.querySelectorAll('.selected-ott img').forEach(img => {
    if (img.dataset.selected !== 'true') {
        img.style.display = 'none';
    }
});
