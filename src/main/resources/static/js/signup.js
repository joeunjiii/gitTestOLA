document.addEventListener("DOMContentLoaded", function () {
    const phoneInput = document.getElementById("phone");

    phoneInput.addEventListener("input", function () {
        this.value = this.value.replace(/[^0-9]/g, ""); // 숫자만 입력되도록 처리
    });
});

document.getElementById("signup-btn").addEventListener("click", function(){
        window.location.href = "/select_genre"
});