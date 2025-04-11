// DOM이 로드되면 리뷰 피드 렌더링 실행
document.addEventListener("DOMContentLoaded", () => {
    renderReviewFeed(); // 실제 리뷰 출력 함수
});

// 리뷰 출력 위치 (HTML의 id="reviewFeed"인 요소)
const reviewFeed = document.getElementById("reviewFeed");

// 백엔드에서 받아올 데이터 예시 (현재는 하드코딩)
const reviewData = [
    {
        user: "강쥐", // 작성자 
        content: "팝콘 진짜 개맛있다! CGV 반반콤보는 무조건이야.", // 리뷰 본문 
        image: "/img/popcorn.jpg", // 리뷰 이미지 
        likes: 150, // 좋아요 수 
        comments: ["나도 반반콤보 좋아해요!", "CU 콘소메도 인정ㅋㅋ"] // 댓글 배열 
    },
    {
        user: "은지",
        content: "달콤한 팝콘에 콜라 한잔이면 끝나지~",
        image: "/img/popcorn.jpg",
        likes: 92,
        comments: []
    }
];
// 리뷰 데이터를 기반으로 HTML 요소 생성 및 렌더링
function renderReviewFeed() {
    reviewData.forEach((r, index) => {
        const section = document.createElement("section");
        section.className = "review-detail";

        section.innerHTML = `
        <div class="review-user-row">
          <div class="user-info">
            <img src="/img/profile.png" class="profile-img" /> 
            <div>
              <p class="nickname">${r.user}</p>
              <p class="ott-title">소용없어 거짓말</p>
            </div>
          </div>
          <button class="follow-btn">팔로우</button>
        </div>
  
        <div class="review-image-wrapper">
          <button class="arrow-btn">◀</button>
          <img src="${r.image}" alt="리뷰 이미지" class="review-img" />
          <button class="arrow-btn">▶</button>
        </div>
  
        <div class="reaction-bar">
          <span class="like-btn" data-index="${index}">❤️ ${r.likes}</span>
          <span>💬 댓글 ${r.comments.length}</span>
        </div>
  
        <p class="review-text">${r.content}</p>
  
        <div class="comment-list" id="comments-${index}">
          ${r.comments.map(c => `<p class="comment">🗨️ ${c}</p>`).join("")}
        </div>
  
        <div class="comment-input">
          <input type="text" placeholder="댓글을 입력하세요" />
          <button class="comment-btn" data-index="${index}">댓글</button>
        </div>
      `;

        reviewFeed.appendChild(section);  // 해당 리뷰 섹션을 HTML에 삽입
    });

    // 좋아요, 댓글, 팔로우 버튼 각각 기능 연결
    setupLikeButtons(); //좋아요
    setupCommentButtons();//댓글
    setupFollowButtons();//팔로우
}

// 좋아요 버튼 클릭 시 좋아요 수 증가 (백엔드라면 PATCH 요청 필요)
function setupLikeButtons() {
    document.querySelectorAll(".like-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            const index = btn.dataset.index; // 어떤 리뷰인지 인덱스로 식별
            reviewData[index].likes++; // 좋아요 수 증가 (프론트 임시 저장)
            btn.textContent = `❤️ ${reviewData[index].likes}`; // 화면에 반영
        });
    });
}
// 댓글 버튼 클릭 시 댓글 리스트에 추가
function setupCommentButtons() {
    document.querySelectorAll(".comment-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            const index = btn.dataset.index;
            const input = btn.previousElementSibling; // 댓글 입력창
            const comment = input.value.trim();

            if (comment) {
                const commentList = document.getElementById(`comments-${index}`);

                // 댓글 HTML 생성 및 출력
                const p = document.createElement("p");
                p.className = "comment";
                p.textContent = `🗨️ ${comment}`;
                commentList.appendChild(p);

                // 입력창 초기화
                input.value = "";
                reviewData[index].comments.push(comment); // 배열에 반영
            }
        });
    });
}
document.querySelectorAll(".profile-img").forEach((img) => {
    if (!img.getAttribute("src") || img.getAttribute("src").trim() === "") {
        img.setAttribute("src", "/LOA/public/images/default_profile.jpg");
    }

    img.onerror = () => {
        img.src = "/LOA/public/images/default_profile.jpg";
    };
});


  