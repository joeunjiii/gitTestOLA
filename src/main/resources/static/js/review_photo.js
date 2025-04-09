document.addEventListener("DOMContentLoaded", () => {
    const selectedTitle = sessionStorage.getItem("selectedTitle");
    if (selectedTitle) {
        document.getElementById("postTitle").value = selectedTitle;
    }
});

document.addEventListener("DOMContentLoaded", () => {
    const selectedTitle = sessionStorage.getItem("selectedTitle");
    if (selectedTitle) {
        document.getElementById("postTitle").value = selectedTitle;
    }

    // "다음" 버튼 클릭 시 form 전송
    document.getElementById("nextBtn").addEventListener("click", () => {
        document.getElementById("uploadForm").submit();
    });
});
