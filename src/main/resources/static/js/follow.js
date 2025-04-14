// follow.js

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

    fetch("/api/follow/users")
        .then(res => res.json())
        .then(({ followers, followings }) => {
            if (followers.length === 0) {
                followerList.innerHTML = '<li class="empty">팔로워가 없습니다.</li>';
            } else {
                followerList.innerHTML = followers.map(user => `
                    <li class="follower">
                        <div class="profile">
                            <img src="/uploads/profile/${user.userId}.jpg" alt="프로필" onerror="this.src='/images/default_profile.jpg'" />
                            <span>${user.nickname}</span>
                        </div>
                        <div class="actions">
                            <button class="message-btn">메시지</button>
                            <span class="remove">✕</span>
                        </div>
                    </li>
                `).join("");
            }

            if (followings.length === 0) {
                followingList.innerHTML = '<li class="empty">팔로잉한 유저가 없습니다.</li>';
            } else {
                followingList.innerHTML = followings.map(user => `
                    <li class="follower">
                        <div class="profile">
                            <img src="/uploads/profile/${user.userId}.jpg" alt="프로필" onerror="this.src='/images/default_profile.jpg'" />
                            <span>${user.nickname}</span>
                        </div>
                        <div class="actions">
                            <button class="message-btn">메시지</button>
                            <span class="remove">✕</span>
                        </div>
                    </li>
                `).join("");
            }

            matchListHeights();
        })
        .catch(err => {
            console.error("팔로우 리스트 불러오기 실패", err);
        });
});

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