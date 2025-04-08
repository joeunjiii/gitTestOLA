package com.smhrd.olaPJ.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smhrd.olaPJ.domain.Genre;
import com.smhrd.olaPJ.domain.User;
import com.smhrd.olaPJ.repository.GenreRepository;
import com.smhrd.olaPJ.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import java.net.URLDecoder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AiServiceClient {

    private final UserRepository userRepository;
    private final GenreRepository genreRepository;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://good-vlmd.onrender.com")
            .build();

    public List<Map<String, Object>> getRecommendation(String username) {
        ObjectMapper objectMapper = new ObjectMapper();

        // 1. 사용자 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        String userId = user.getUserId();

        // 2. 장르 조회
        Genre genre = genreRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("장르 정보 없음"));

        // 3. 선택된 장르 수집
        List<String> selectedGenres = new ArrayList<>();
        if (genre.getRomance() == 'Y') selectedGenres.add("로맨스");
        if (genre.getComedy() == 'Y') selectedGenres.add("코미디");
        if (genre.getThriller() == 'Y') selectedGenres.add("스릴러");
        if (genre.getAnimation() == 'Y') selectedGenres.add("애니메이션");
        if (genre.getAction() == 'Y') selectedGenres.add("액션");
        if (genre.getDrama() == 'Y') selectedGenres.add("드라마");
        if (genre.getHorror() == 'Y') selectedGenres.add("공포");
        if (genre.getFantasy() == 'Y') selectedGenres.add("판타지");

        // 4. OTT 플랫폼 정보 가져오기 (공백 제거 처리 포함)
        String ottPlatformRaw = genre.getOttPlatform(); // 예: "쿠팡플레이,티빙"
        List<String> selectedOtts = new ArrayList<>();
        if (ottPlatformRaw != null && !ottPlatformRaw.isEmpty()) {
            selectedOtts = Arrays.stream(ottPlatformRaw.split(","))
                    .map(String::trim) // ✅ 공백 제거!
                    .filter(s -> !s.isEmpty()) // 빈 문자열 방지
                    .toList();
        }

        // 5. 한글 인코딩 적용
        List<String> encodedGenres = selectedGenres.stream()
                .map(g -> URLEncoder.encode(g, StandardCharsets.UTF_8))
                .toList();
        List<String> encodedOtts = selectedOtts.stream()
                .map(o -> URLEncoder.encode(o, StandardCharsets.UTF_8))
                .toList();

        // 6. URI 생성
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString("https://good-vlmd.onrender.com/recommend");

        for (String genreStr : encodedGenres) {
            builder.queryParam("user_genre", genreStr);
        }

        for (String ott : encodedOtts) {
            builder.queryParam("user_ott", ott);
        }

        builder.queryParam("prefer_new", genre.isLatestYear())
                .queryParam("selected_title", "")
                .queryParam("total_needed", 5);

        URI uri = builder.build(true).toUri(); // true로 자동 인코딩

        // 👇 한글 보기용 출력
        System.out.println("✅ [디코딩된 URI] " + URLDecoder.decode(uri.toString(), StandardCharsets.UTF_8));
        // 👇 실제 호출 URI
        System.out.println("✅ [실제 요청 URI] " + uri);

        // 7. API 호출
        Mono<String> responseMono = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);

        try {
            String jsonStr = responseMono.block();
            JsonNode root = objectMapper.readTree(jsonStr);
            JsonNode results = root.path("results");

            List<Map<String, Object>> contentList = new ArrayList<>();
            for (JsonNode item : results) {
                Map<String, Object> content = new HashMap<>();
                content.put("title", item.path("CONTENTS_TITLE").asText());
                content.put("poster", item.path("POSTER_IMG").asText());
                double similarity = item.path("유사도").asDouble();
                content.put("similarity", similarity);
                System.out.println("🎬 " + content.get("title") + " | 유사도: " + similarity);
                contentList.add(content);
            }

            return contentList;

        } catch (Exception e) {
            System.out.println("❗ 예외 발생:");
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
