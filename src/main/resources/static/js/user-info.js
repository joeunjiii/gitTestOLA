document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('profileForm');
    const profileImage = document.getElementById('profileImage');
    const photoInput = document.getElementById('photoInput');
    const nicknameInput = document.getElementById('nickname');
    const bioInput = document.getElementById('bio');
    const saveBtn = document.getElementById('saveBtn');

    // ✅ 프로필 이미지 클릭 시 파일 선택창 열기
    profileImage?.addEventListener('click', () => {
        photoInput?.click();
    });

    // ✅ 선택한 이미지 미리보기
    photoInput?.addEventListener('change', () => {
        const file = photoInput.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = e => {
                profileImage.src = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    });

    // ✅ 폼 submit 이벤트 가로채기 → fetch로 비동기 전송
    form?.addEventListener('submit', async (e) => {
        e.preventDefault();

        const nickname = nicknameInput?.value?.trim();
        const introduce = bioInput?.value?.trim();

        const allGenres = ['romance', 'comedy', 'thriller', 'animation', 'action', 'drama', 'horror', 'fantasy'];
        const formData = new FormData();

        if (nickname) formData.append("nickname", nickname);
        if (introduce) formData.append("introduce", introduce);

        // ✅ 장르 체크값 설정
        allGenres.forEach(genre => {
            const checkbox = document.querySelector(`input[name="genres"][value="${genre}"]`);
            const isChecked = checkbox?.checked ? 'Y' : 'N';
            formData.append(`genres[${genre}]`, isChecked);
        });

        // ✅ 프로필 이미지가 선택되어 있을 경우
        if (photoInput.files[0]) {
            formData.append("profileImg", photoInput.files[0]);
        }

        // ✅ 저장 중 버튼 비활성화
        saveBtn.disabled = true;
        saveBtn.innerText = "저장 중...";

        try {
            const response = await fetch("/mypage/update", {
                method: "POST",
                body: formData
            });

            if (response.ok) {
                location.href = "/mypage";
            } else {
                const text = await response.text();
                console.error("서버 응답 오류:", text);
                alert("저장에 실패했습니다.");
            }
        } catch (error) {
            console.error("네트워크 오류:", error);
            alert("서버 오류가 발생했습니다.");
        } finally {
            saveBtn.disabled = false;
            saveBtn.innerText = "저장";
        }
    });
});
