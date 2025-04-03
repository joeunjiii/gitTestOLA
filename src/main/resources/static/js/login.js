// JavaScript 코드 (js/script.js)

document.addEventListener("DOMContentLoaded", function () {
    // 비밀번호 찾기 버튼 클릭 시 페이지 이동
    document.getElementById("find-pw-btn").addEventListener("click", function (event) {
        event.preventDefault(); // 기본 동작 방지
        window.location.href = "pwfind.html"; // 비밀번호 찾기 페이지로 이동
    });
});

document.addEventListener("DOMContentLoaded", function()  {

    document.getElementById("find-id-btn").addEventListener("click", function (event) {
        event.preventDefault();
        window.location.href = "idfind.html";
    });
});

document.addEventListener("DOMContentLoaded", function()  {

    document.getElementById("create-btn").addEventListener("click", function (event) {
        event.preventDefault();
        window.location.href = "/signup";  //절대 경로 사용
    });
});

document.addEventListener("DOMContentLoaded", function()  {

    document.getElementById("find-id-btn").addEventListener("click", function (event) {
        event.preventDefault();
        window.location.href = "idfind.html";
    });
});