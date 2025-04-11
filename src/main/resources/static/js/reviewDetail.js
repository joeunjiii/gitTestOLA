document.addEventListener("DOMContentLoaded", function () {
    const params = new URLSearchParams(window.location.search);
    const title = params.get("title");

    if (!title) return; // 🔸 title이 없으면 아예 동작 안 함

    // 🔹 콘텐츠 상세 정보 및 리뷰 불러오기
    fetch(`/api/content/detail?title=${encodeURIComponent(title)}`)
        .then(res => res.json())
        .then(data => {
            console.log("✅ API 결과:", data);
            renderContent(data.content);
            renderReviews(data.reviews);
        })
        .catch(err => {
            console.error("데이터 로딩 오류", err);
        });

    function renderContent(content) {
        const section = document.getElementById("content-detail");

        section.innerHTML = `
        <div class="content-detail-box">
            <img src="${content.posterImg}" alt="poster" class="poster-img" />

            <div class="content-meta">
                <div style="display: flex; justify-content: space-between; align-items: center;">
                    <h2>${content.title}</h2>
                    <button class="bookmark-btn" id="bookmarkBtn">☆ 찜하기</button>
                </div>

                <p class="synopsis-text" id="synopsis">${content.synopsis}</p>
                <span class="more-btn" id="toggleMore">...더보기</span>

                <table class="info-table">
                    <tr><td>🌟 평점</td><td>${content.rating}</td></tr>
                    <tr><td>🎬 장르</td><td>${content.contentsGenre}</td></tr>
                    <tr><td>📅 제작 연도</td><td>${content.releaseYear}년</td></tr>
                    <tr><td>🔞 시청 등급</td><td>${content.ageRating}</td></tr>
                    <tr><td>🎥 감독</td><td>${content.director}</td></tr>
                    <tr><td>🎭 출연진</td><td>${content.cast}</td></tr>
                    <tr><td>📺 OTT</td><td>${content.ott}</td></tr>
                </table>
            </div>
        </div>
    `;

        // ✅ 줄거리 ...더보기/간략히 토글
        const toggleBtn = document.getElementById("toggleMore");
        const synopsisEl = document.getElementById("synopsis");
        toggleBtn.addEventListener("click", () => {
            synopsisEl.classList.toggle("expanded");
            toggleBtn.textContent = synopsisEl.classList.contains("expanded") ? "...간략히" : "...더보기";
        });

        // ✅ 찜 버튼 토글
        const bookmarkBtn = document.getElementById("bookmarkBtn");
        if (bookmarkBtn) {
            let isBookmarked = false;
            bookmarkBtn.addEventListener("click", () => {
                isBookmarked = !isBookmarked;
                bookmarkBtn.textContent = isBookmarked ? "★ 찜" : "☆ 찜";
                bookmarkBtn.classList.toggle("active");
            });
        }
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
