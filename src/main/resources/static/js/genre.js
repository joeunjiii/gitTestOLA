
document.getElementById("selectComplete").addEventListener("click", async function () {
    try {
        // 현재 로그인된 사용자 ID 가져오기
        const userResponse = await fetch("/api/user/current");
        if (!userResponse.ok) throw new Error("사용자 정보를 가져오는 데 실패했습니다.");

        const userData = await userResponse.json();
        const userId = userData.username; //

        // 선택된 장르 체크박스 가져오기
        const selectedGenres = Array.from(document.querySelectorAll("input[name='genre']:checked"))
            .map(checkbox => checkbox.value);

        console.log("선택된 장르 목록:", selectedGenres); // 디버깅용 로그 추가

        if (selectedGenres.length === 0) {
            alert("최소 하나 이상의 장르를 선택해주세요.");
            return;
        }

        const genres = ["romance", "comedy", "thriller", "animation", "action", "drama","horror", "fantasy"];
        const requestData = {userId : userId};

        genres.forEach(genre =>{

            requestData[genre] = selectedGenres.includes(genre) ? "Y" : "N";
        });


        console.log("서버로 보낼 데이터:", requestData); // 디버깅용 로그 추가

        // 장르 데이터 서버로 전송 (POST 요청)
        const response = await fetch("/genre/save", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(requestData)
        });
    }catch(error) {
        console.error("에러 발생:", error);
        alert("장르 정보 저장 문제 발생");
    }
});
