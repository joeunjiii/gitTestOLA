/* 팔로우 페이지 js */

// DOM이 모두 로드되었을 때 실행
document.addEventListener("DOMContentLoaded", () => {
    const followerTab = document.getElementById("follower-tab");       // 팔로워 탭 요소
    const followingTab = document.getElementById("following-tab");     // 팔로잉 탭 요소
    const followerList = document.getElementById("follower-list");     // 팔로워 목록 요소
    const followingList = document.getElementById("following-list");   // 팔로잉 목록 요소

    // ▶ 두 리스트 중 더 긴 쪽의 높이를 기준으로 맞추는 함수
    const matchListHeights = () => {
        const followerHeight = followerList.scrollHeight;               // 팔로워 목록의 실제 높이
        const followingHeight = followingList.scrollHeight;             // 팔로잉 목록의 실제 높이
        const maxHeight = Math.max(followerHeight, followingHeight);    // 더 큰 쪽의 높이 선택

        // 두 리스트 모두 최소 높이를 동일하게 설정
        followerList.style.minHeight = `${maxHeight}px`;
        followingList.style.minHeight = `${maxHeight}px`;
    };

    // ▶ 팔로워 탭 클릭 시 실행되는 이벤트
    followerTab.addEventListener("click", () => {
        followerTab.classList.add("active");         // 팔로워 탭에 active 클래스 추가
        followingTab.classList.remove("active");     // 팔로잉 탭에서는 active 클래스 제거
        followerList.style.display = "block";        // 팔로워 목록 보이기
        followingList.style.display = "none";        // 팔로잉 목록 숨기기
        matchListHeights();                          // 리스트 높이 맞추기
    });

    // ▶ 팔로잉 탭 클릭 시 실행되는 이벤트
    followingTab.addEventListener("click", () => {
        followingTab.classList.add("active");        // 팔로잉 탭에 active 클래스 추가
        followerTab.classList.remove("active");      // 팔로워 탭에서는 active 클래스 제거
        followerList.style.display = "none";         // 팔로워 목록 숨기기
        followingList.style.display = "block";       // 팔로잉 목록 보이기
        matchListHeights();                          // 리스트 높이 맞추기
    });

    // ▶ 페이지가 처음 로딩될 때 리스트 높이 맞추기
    matchListHeights();
});

// 두 번째 DOMContentLoaded는 검색창 관련 기능
document.addEventListener("DOMContentLoaded", function () {
    const searchInput = document.querySelector(".search-box input");   // 검색창 input 요소

    // ▶ input이 포커스 되었을 때 커서 색을 진하게 설정 (보이게)
    searchInput.addEventListener("focus", () => {
        searchInput.style.caretColor = "#222";  // 진한 회색으로 커서 표시
    });

    // ▶ input에서 포커스가 벗어났을 때, 값이 없으면 커서 숨김
    searchInput.addEventListener("blur", () => {
        if (searchInput.value.trim() === "") {
            searchInput.style.caretColor = "transparent";  // 커서 숨기기
        }
    });

    // ▶ 페이지 로딩 시에도 input 값이 비어있으면 커서 숨기기
    if (searchInput.value.trim() === "") {
        searchInput.style.caretColor = "transparent";
    } else {
        searchInput.style.caretColor = "#222";
    }
});

document.addEventListener("DOMContentLoaded", () => {
    const profileImgs = document.querySelectorAll(".profile img"); // 프로필 이미지 전부 선택

    profileImgs.forEach((img) => {
        // 이미지 경로가 없거나 잘못되었을 경우 기본 이미지로 대체
        if (!img.getAttribute("src") || img.getAttribute("src").trim() === "") {
            img.setAttribute("src", "/images/default_profile.jpg");
        }

        // 이미지 로드 실패 시에도 기본 이미지로 대체
        img.onerror = () => {
            img.src = "/images/default_profile.jpg";
        };
    });
});