
function scrollLeft() {
    document.querySelector('.scroll-list').scrollBy({ left: -200, behavior: 'smooth' });
}

function scrollRight() {
    document.querySelector('.scroll-list').scrollBy({ left: 200, behavior: 'smooth' });
}

document.querySelectorAll('.scroll-item').forEach(item => {
    item.addEventListener('click', () => {
        alert("이 작품을 클릭했습니다!");
    });
});

document.querySelectorAll('.review-item').forEach(item => {
    item.addEventListener('click', () => {
        alert("리뷰를 클릭했습니다!");
    });
});

document.querySelectorAll('.selected-ott img').forEach(img => {
    if (img.dataset.selected !== 'true') {
        img.style.display = 'none';
    }
});