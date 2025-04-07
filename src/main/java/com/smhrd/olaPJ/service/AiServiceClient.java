package com.smhrd.olaPJ.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class AiServiceClient {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://good-vlmd.onrender.com")
            .build();

    public List<Map<String, Object>> getRecommendation() {
        ObjectMapper objectMapper = new ObjectMapper();

        Mono<String> responseMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/recommend")
                        .queryParam("user_ott", "넷플릭스")
                        .queryParam("user_ott", "티빙")
                        .queryParam("user_genre", "드라마")
                        .queryParam("user_genre", "스릴러")
                        .queryParam("user_genre", "액션")
                        .queryParam("prefer_new", true)
                        .queryParam("selected_title", "")
                        .queryParam("total_needed", 5)
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class);

        try {
            String jsonStr = responseMono.block();  // 동기적으로 받기
            System.out.println("✅ 응답 본문: " + jsonStr);

            JsonNode root = objectMapper.readTree(jsonStr);
            JsonNode results = root.path("results");

            List<Map<String, Object>> contentList = new ArrayList<>();
            for (JsonNode item : results) {
                Map<String, Object> content = new HashMap<>();
                content.put("title", item.path("CONTENTS_TITLE").asText());
                content.put("poster", item.path("POSTER_IMG").asText());

                // 🔥 유사도 추가 출력
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
