document.getElementById("selectComplete").addEventListener("click", async function () {
    try {
        // 현재 로그인된 사용자 ID 가져오기
        const userResponse = await fetch("/api/user/current");
        if (!userResponse.ok) throw new Error("사용자 정보를 가져오는 데 실패했습니다.");

        const userData = await userResponse.json();
        const userId = userData.username; // `UserResponse`에 맞게 필드명을 변경

        // 선택된 장르 체크박스 가져오기
        const selectedGenres = Array.from(document.querySelectorAll("input[name='genre']:checked"))
            .map(checkbox => checkbox.value);

        console.log("선택된 장르 목록:", selectedGenres); // 디버깅용 로그 추가

        if (selectedGenres.length === 0) {
            alert("최소 하나 이상의 장르를 선택해주세요.");
            return;
        }

        // 서버로 전송할 데이터 객체 생성
        const requestData = {
            userId: userId,
            romance: selectedGenres.includes("romance") ? "Y" : "N",
            comedy: selectedGenres.includes("comedy") ? "Y" : "N",
            thriller: selectedGenres.includes("thriller") ? "Y" : "N",
            animation: selectedGenres.includes("animation") ? "Y" : "N",
            action: selectedGenres.includes("action") ? "Y" : "N",
            drama: selectedGenres.includes("drama") ? "Y" : "N",
            horror: selectedGenres.includes("horror") ? "Y" : "N",
            fantasy: selectedGenres.includes("fantasy") ? "Y" : "N"
        };

        console.log("서버로 보낼 데이터:", requestData); // 디버깅용 로그 추가

        // 장르 데이터 서버로 전송 (POST 요청)
        const response = await fetch("/genre/save", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(requestData)
        });

        if (!response.ok) throw new Error("서버 오류 발생");

        alert("장르가 저장되었습니다.");
        window.location.href = "/main"; // 메인 페이지로 이동
    } catch (error) {
        console.error("오류 발생:", error);
        alert("장르 저장 중 오류가 발생했습니다.");
    }
});
