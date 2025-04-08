
const navItems = document.querySelectorAll(".nav-item");
navItems.forEach((item) => {
    item.addEventListener("click", () => {
        navItems.forEach((el) => el.classList.remove("active"));
        item.classList.add("active");
    });
});

// 👉 슬라이드 좌우 버튼 작동
const leftBtn = document.querySelector(".arrow.left");
const rightBtn = document.querySelector(".arrow.right");
const slideTrack = document.querySelector(".ott-slide-track");

const scrollAmount = 300;

leftBtn.addEventListener("click", () => {
    slideTrack.scrollBy({
        left: -scrollAmount,
        behavior: "smooth"
    });
});

rightBtn.addEventListener("click", () => {
    slideTrack.scrollBy({
        left: scrollAmount,
        behavior: "smooth"
    });
});