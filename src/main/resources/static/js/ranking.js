document.addEventListener("DOMContentLoaded", () => {
    const buttons = document.querySelectorAll(".tab-btn");
    const list = document.getElementById("ranking-list");

    const fetchAndRender = async (type) => {
        try {
            const res = await fetch(`/ranking/${type}`);
            const data = await res.json();

            list.innerHTML = "";

            data.slice(0, 100).forEach((item, index) => {
                const li = document.createElement("li");
                li.className = "ranking-item";

                li.innerHTML = `
                    <div class="rank-num">${index + 1}</div>
                    <img src="${item.posterImg || '/images/default_poster.jpg'}"
                         class="poster-img clickable"
                         alt="${item.title}" />
                    <div class="info">
                        <div class="title clickable">${item.title}</div>
                        <div class="meta">${item.genre} · ${item.releaseYear}</div>
                    </div>
                `;

                // 이미지 또는 제목 클릭 시 이동
                li.querySelectorAll(".clickable").forEach((elem) => {
                    elem.style.cursor = "pointer";
                    elem.addEventListener("click", (e) => {
                        e.stopPropagation(); // li 전체 클릭 방지
                        window.location.href = `/reviewDetail?title=${encodeURIComponent(item.title)}`;
                    });
                });

                list.appendChild(li);
            });

        } catch (err) {
            console.error("랭킹 데이터 로딩 실패", err);
        }
    };

    fetchAndRender("reviews");

    buttons.forEach((btn) => {
        btn.addEventListener("click", () => {
            buttons.forEach(b => b.classList.remove("active"));
            btn.classList.add("active");
            fetchAndRender(btn.dataset.type);
        });
    });
});
