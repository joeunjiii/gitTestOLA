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

    // ✅ 팔로워/팔로잉 영역 클릭 시 JS로 페이지 이동
    const followerCountBox = document.querySelector('#follower-count')?.parentElement;
    const followingCountBox = document.querySelector('#following-count')?.parentElement;

    if (followerCountBox) {
        followerCountBox.addEventListener("click", () => {
            window.location.href = "/follow/list#follower";
        });
    }

    if (followingCountBox) {
        followingCountBox.addEventListener("click", () => {
            window.location.href = "/follow/list#following";
        });
    }

    // ✅ 팔로워/팔로잉 수 비동기 조회
    fetch("/api/follow/counts")
        .then(res => res.json())
        .then(data => {
            document.getElementById("follower-count").textContent = data.followerCount;
            document.getElementById("following-count").textContent = data.followingCount;
        })
        .catch(err => {
            console.error("❌ 팔로우 수 조회 실패:", err);
        });

});

// 선택되지 않은 OTT 숨기기
document.querySelectorAll('.selected-ott img').forEach(img => {
    if (img.dataset.selected !== 'true') {
        img.style.display = 'none';
    }
});
