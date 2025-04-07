document.getElementById("selectComplete").addEventListener("click", async function () {
    try {
        // 현재 로그인된 사용자 정보 요청
        const userResponse = await fetch("/api/user/current", {
            credentials: "include"
        });

        const contentType = userResponse.headers.get("Content-Type");
        if (!userResponse.ok || !contentType.includes("application/json")) {
            return new Error("인증된 사용자만 접근할 수 있습니다.");
        }

        //const userData = await userResponse.json();

        // 선택된 장르 체크박스 추출
        const selectedGenres = Array.from(document.querySelectorAll("input[name='genre']:checked"))
            .map(cb => cb.value);

        if (selectedGenres.length === 0) {
            alert("최소 하나 이상의 장르를 선택해주세요.");
            return;
        }

        // 기본값 모두 "N"
        const requestData = {
            romance: "N", comedy: "N", thriller: "N", animation: "N",
            action: "N", drama: "N", horror: "N", fantasy: "N"
        };

        // 선택된 장르만 "Y"로 설정
        selectedGenres.forEach(genre => {
            if (requestData.hasOwnProperty(genre)) {
                requestData[genre] = "Y";
            }
        });

        // 서버로 전송
        const response = await fetch("/genre/save", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "include",
            body: JSON.stringify(requestData)
        });

        if (response.ok) {
            window.location.href = "/main";
        } else {
            alert("장르 저장에 실패했습니다.");
        }

    } catch (error) {
        console.error("에러 발생:", error);
        alert("장르 정보 저장 중 문제가 발생했습니다.");
    }
});
