const navItems = document.querySelectorAll(".nav-item");
navItems.forEach((item) => {
    item.addEventListener("click", () => {
        navItems.forEach((el) => el.classList.remove("active"));
        item.classList.add("active");
    });
});

document.addEventListener("DOMContentLoaded", function(){
    const ottList = JSON.parse(localStorage.getItem("selectedOtt")) || [];

    if (ottList.length > 0) {
        fetch("genre/save-ott", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ ottPlatform: ottList })
        }).then(res => {
            if (res.ok) {
                console.log("OTT 저장 완료");
                localStorage.removeItem("selectedOtt"); // 저장 후 정리
            } else {
                console.error("OTT 저장 실패");
            }
        });
    }
});