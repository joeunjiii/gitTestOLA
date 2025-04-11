document.addEventListener("DOMContentLoaded", function () {
    const params = new URLSearchParams(window.location.search);
    const title = params.get("title");

    if (!title) return; // 🔸 title이 없으면 아예 동작 안 함

    // 🔹 콘텐츠 상세 정보 및 리뷰 불러오기
    fetch(`/api/content/detail?title=${encodeURIComponent(title)}`)
        .then(res => res.json())
        .then(data => {
            renderContent(data.content);
            renderReviews(data.reviews);
        })
        .catch(err => {
            console.error("데이터 로딩 오류", err);
        });

    function renderContent(content) {
        const section = document.getElementById("content-detail");
        section.innerHTML = `
            <div class="detail-left">
                <img src="${content.posterImg}" alt="poster" class="poster-img" />
            </div>
            <div class="detail-right">
                <div class="bookmark-box"><button class="bookmark-btn">☆</button></div>
                <div class="user-info">
                    <div class="user-meta"><p class="ott-title">${content.title}</p></div>
                </div>
                <p class="review-text">${content.synopsis}</p>
            </div>
        `;
    }

    function renderReviews(reviews) {
        const reviewSection = document.getElementById("review-list");
        reviewSection.innerHTML = "";

        reviews.forEach(post => {
            const reviewEl = document.createElement("section");
            reviewEl.className = "review-detail";
            reviewEl.innerHTML = `
                <div class="review-user-row">
                    <div class="user-info">
                        <img src="/img/profile.png" class="profile-img" />
                        <div>
                            <p class="nickname">${post.nickname}</p>
                            <p class="ott-title">${post.postTitle}</p>
                        </div>
                    </div>
                    <button class="follow-btn">팔로우</button>
                </div>

                <div class="review-image-wrapper">
                    ${[post.postFile1, post.postFile2, post.postFile3]
                .filter(Boolean)
                .map(f => `<img src="/uploads/${f}" class="review-img" />`)
                .join("")}
                </div>

                <div class="reaction-bar">
                    <span class="like-btn">❤️ ${post.postRating ?? 0}</span>
                    <span>💬 댓글</span>
                </div>

                <p class="review-text">${post.postContent ?? ""}</p>

                <div class="comment-list" id="comments-${post.postSeq}"></div>

                <div class="comment-input">
                    <input type="text" placeholder="댓글을 입력하세요" />
                    <button class="comment-btn" data-postseq="${post.postSeq}">댓글</button>
                </div>
            `;

            reviewSection.appendChild(reviewEl);

            // 🔹 댓글 불러오기
            fetch(`/api/comments/${post.postSeq}`)
                .then(res => res.json())
                .then(comments => {
                    const commentBox = document.getElementById(`comments-${post.postSeq}`);
                    commentBox.innerHTML = comments.map(c => `<p class="comment">🗨️ ${c.content}</p>`).join("");
                });
        });
    }
});
