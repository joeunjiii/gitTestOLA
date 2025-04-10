// 다음 버튼 눌렀을 때 다음 페이지로 이동
document.addEventListener('DOMContentLoaded', () => {
    const nextBtn = document.getElementById('nextBtn');
    if (nextBtn) {
        nextBtn.addEventListener('click', () => {
            window.location.href = 'logins2.html';
        });
    }
});

document.addEventListener('DOMContentLoaded', () => {
    const profileImage = document.getElementById('profileImage');
    const photoInput = document.getElementById('photoInput');

    // 프로필 이미지를 클릭하면 → 파일 선택창 열림
    profileImage.addEventListener('click', () => {
        photoInput.click();
    });

    // 이미지 선택 → 즉시 프로필에 반영
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
});
document.addEventListener('DOMContentLoaded', () => {
    const nicknameInput = document.getElementById('nickname');
    const editBtn = document.getElementById('editNicknameBtn');

    let isEditable = true; // 처음엔 입력 가능하게 시작

    editBtn.addEventListener('click', () => {
        isEditable = !isEditable;

        if (isEditable) {
            nicknameInput.removeAttribute('readonly');
            nicknameInput.focus(); // 커서 자동 포커스
        } else {
            nicknameInput.setAttribute('readonly', true); // 편집 잠금
        }
    });
});

