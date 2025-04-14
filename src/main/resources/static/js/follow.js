window.addEventListener("DOMContentLoaded", () => {
    const followerTab = document.getElementById("follower-tab");
    const followingTab = document.getElementById("following-tab");
    const followerList = document.getElementById("follower-list");
    const followingList = document.getElementById("following-list");

    const hash = window.location.hash;
    if (hash === "#following") {
        followingTab.classList.add("active");
        followerTab.classList.remove("active");
        followerList.style.display = "none";
        followingList.style.display = "block";
    } else {
        followerTab.classList.add("active");
        followingTab.classList.remove("active");
        followerList.style.display = "block";
        followingList.style.display = "none";
    }

    const matchListHeights = () => {
        const maxHeight = Math.max(followerList.scrollHeight, followingList.scrollHeight);
        followerList.style.minHeight = `${maxHeight}px`;
        followingList.style.minHeight = `${maxHeight}px`;
    };

    followerTab.addEventListener("click", () => {
        followerTab.classList.add("active");
        followingTab.classList.remove("active");
        followerList.style.display = "block";
        followingList.style.display = "none";
        matchListHeights();
    });

    followingTab.addEventListener("click", () => {
        followingTab.classList.add("active");
        followerTab.classList.remove("active");
        followerList.style.display = "none";
        followingList.style.display = "block";
        matchListHeights();
    });

    // ✅ 팔로우 수 갱신
    const updateCounts = () => {
        fetch("/api/follow/counts")
            .then(res => res.json())
            .then(data => {
                document.getElementById("follower-count").textContent = data.followerCount;
                document.getElementById("following-count").textContent = data.followingCount;
            })
            .catch(err => console.error("❌ 팔로우 수 갱신 실패:", err));
    };

    // ✅ 리스트 렌더링 함수
    const renderLists = () => {
        fetch("/api/follow/users")
            .then(res => res.json())
            .then(({ followers, followings }) => {
                // 팔로워 리스트
                followerList.innerHTML = followers.length === 0
                    ? '<li class="empty">팔로워가 없습니다.</li>'
                    : followers.map(user => `
                        <li class="follower" data-user-id="${user.userId}" data-nickname="${user.nickname}">
                            <div class="profile">
                                <img src="/uploads/profile/${user.userId}.jpg" alt="프로필" onerror="this.src='/images/default_profile.jpg'" />
                                <span>${user.nickname}</span>
                            </div>
                            <div class="actions">
                                ${user.isFollowing === "true" ? "" : `<button class="follow-btn">팔로우</button>`}
                            </div>
                        </li>
                    `).join("");

                // 팔로잉 리스트
                followingList.innerHTML = followings.length === 0
                    ? '<li class="empty">팔로잉한 유저가 없습니다.</li>'
                    : followings.map(user => `
                        <li class="follower" data-user-id="${user.userId}" data-nickname="${user.nickname}">
                            <div class="profile">
                                <img src="/uploads/profile/${user.userId}.jpg" alt="프로필" onerror="this.src='/images/default_profile.jpg'" />
                                <span>${user.nickname}</span>
                            </div>
                            <div class="actions">
                                <span class="remove">✕</span>
                            </div>
                        </li>
                    `).join("");

                matchListHeights();
            })
            .catch(err => {
                console.error("❌ 팔로우 리스트 불러오기 실패", err);
            });
    };

    // ✅ 초기 실행
    renderLists();
    updateCounts();

    // ✅ 이벤트 위임: 언팔 / 팔로우
    document.addEventListener("click", (e) => {
        const li = e.target.closest("li.follower");
        if (!li) return;

        const userId = li.dataset.userId;
        const nickname = li.dataset.nickname;

        // 언팔로우
        if (e.target.classList.contains("remove")) {
            if (confirm(`${nickname}님을 언팔로우하시겠습니까?`)) {
                fetch("/api/follow", {
                    method: "DELETE",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ followee: userId })
                })
                    .then(res => {
                        if (!res.ok) throw new Error("서버 오류");
                        li.remove();
                        updateCounts();
                        renderLists();
                        console.log(`✅ ${nickname} 언팔로우 완료`);
                    })
                    .catch(err => {
                        alert("❌ 언팔로우 처리 중 오류 발생");
                        console.error("언팔로우 실패", err);
                    });
            }
        }

        // 팔로우
        if (e.target.classList.contains("follow-btn")) {
            fetch("/api/follow", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ followee: userId })
            })
                .then(res => {
                    if (!res.ok) throw new Error("서버 오류");
                    updateCounts();
                    renderLists();
                    console.log(`✅ ${nickname} 팔로우 완료`);
                })
                .catch(err => {
                    alert("❌ 팔로우 처리 중 오류 발생");
                    console.error("팔로우 실패", err);
                });
        }
    });
});

// 검색창 커서 처리
document.addEventListener("DOMContentLoaded", () => {
    const searchInput = document.querySelector(".search-box input");
    searchInput.addEventListener("focus", () => {
        searchInput.style.caretColor = "#222";
    });
    searchInput.addEventListener("blur", () => {
        searchInput.style.caretColor = searchInput.value.trim() === "" ? "transparent" : "#222";
    });
    searchInput.style.caretColor = searchInput.value.trim() === "" ? "transparent" : "#222";
});
