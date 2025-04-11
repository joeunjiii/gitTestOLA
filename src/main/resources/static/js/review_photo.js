document.addEventListener("DOMContentLoaded", () => {
    const selectedTitle = sessionStorage.getItem("selectedTitle");
    const selectedContentId = sessionStorage.getItem("selectedContentId");

    console.log("🎯 selectedTitle:", selectedTitle);
    console.log("🎯 selectedContentId:", selectedContentId); // 👈 확인용

    if (selectedTitle) {
        document.getElementById("postTitle").value = selectedTitle;
    }

    if (selectedContentId) {
        document.getElementById("contentId").value = selectedContentId;
    } else {
        alert("❌ contentId가 설정되지 않았습니다. 이전 화면에서 설정되었는지 확인해주세요.");
    }

    document.getElementById("nextBtn").addEventListener("click", () => {
        document.getElementById("uploadForm").submit();
    });
});
