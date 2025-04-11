document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('profileForm');
    const profileImage = document.getElementById('profileImage');
    const photoInput = document.getElementById('photoInput');
    const nicknameInput = document.getElementById('nickname');
    const bioInput = document.getElementById('bio');
    const genreCheckboxes = document.querySelectorAll('.genre-grid input[type="checkbox"]');
    const saveBtn = document.getElementById('saveBtn');

    profileImage.addEventListener('click', () => photoInput.click());
    photoInput.addEventListener('change', () => {
        const file = photoInput.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = e => {
                profileImage.src = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    });

    // 🔥 form 기본 제출 막고 fetch로 전송
    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const nickname = nicknameInput.value;
        const introduce = bioInput.value;

        const allGenres = ['romance', 'comedy', 'thriller', 'animation', 'action', 'drama', 'horror', 'fantasy'];
        const formData = new FormData();

        formData.append("nickname", nickname);
        formData.append("introduce", introduce);

        // 모든 장르에 대해 Y/N 체크 후 formData에 추가
        allGenres.forEach(g => {
            const checkbox = document.querySelector(`input[name="genres"][value="${g}"]`);
            formData.append(`genres[${g}]`, checkbox.checked ? 'Y' : 'N');
        });

        if (photoInput.files[0]) {
            formData.append("profileImg", photoInput.files[0]);
        }

        try {
            const response = await fetch("/mypage/update", {
                method: "POST",
                body: formData
            });

            if (response.ok) {
                alert("프로필이 성공적으로 수정되었습니다!");
                location.href = "/mypage";
            } else {
                alert("저장에 실패했습니다.");
            }
        } catch (err) {
            console.error("서버 오류 발생:", err);
            alert("서버 오류가 발생했습니다.");
        }
    });
});
