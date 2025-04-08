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

    // ✅ 기본 추천
    public List<Map<String, Object>> getBasicRecommendation(String username) {
        ObjectMapper objectMapper = new ObjectMapper();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        String userId = user.getUserId();

        Genre genre = genreRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("장르 정보 없음"));

        List<String> selectedGenres = new ArrayList<>();
        if (genre.getRomance() == 'Y') selectedGenres.add("로맨스");
        if (genre.getComedy() == 'Y') selectedGenres.add("코미디");
        if (genre.getThriller() == 'Y') selectedGenres.add("스릴러");
        if (genre.getAnimation() == 'Y') selectedGenres.add("애니메이션");
        if (genre.getAction() == 'Y') selectedGenres.add("액션");
        if (genre.getDrama() == 'Y') selectedGenres.add("드라마");
        if (genre.getHorror() == 'Y') selectedGenres.add("공포");
        if (genre.getFantasy() == 'Y') selectedGenres.add("판타지");

        String ottPlatformRaw = genre.getOttPlatform();
        List<String> selectedOtts = new ArrayList<>();
        if (ottPlatformRaw != null && !ottPlatformRaw.isEmpty()) {
            selectedOtts = Arrays.stream(ottPlatformRaw.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();
        }

        List<String> encodedGenres = selectedGenres.stream()
                .map(g -> URLEncoder.encode(g, StandardCharsets.UTF_8))
                .toList();
        List<String> encodedOtts = selectedOtts.stream()
                .map(o -> URLEncoder.encode(o, StandardCharsets.UTF_8))
                .toList();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString("https://good-vlmd.onrender.com/recommend/basic");

        for (String genreStr : encodedGenres) {
            builder.queryParam("user_genre", genreStr);
        }
        for (String ott : encodedOtts) {
            builder.queryParam("user_ott", ott);
        }

        builder.queryParam("prefer_new", genre.isLatestYear())
                .queryParam("total_needed", 5);

        URI uri = builder.build(true).toUri();

        System.out.println("✅ [기본추천 URI 디코딩] " + URLDecoder.decode(uri.toString(), StandardCharsets.UTF_8));

        Mono<String> responseMono = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);

        try {
            String jsonStr = responseMono.block();
            JsonNode results = objectMapper.readTree(jsonStr).path("results");

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
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // ✅ 선택 콘텐츠 기반 추천
    public List<Map<String, Object>> getSelectedRecommendation(String username, String selectedTitle) {
        ObjectMapper objectMapper = new ObjectMapper();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        String userId = user.getUserId();

        Genre genre = genreRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("장르 정보 없음"));

        List<String> selectedGenres = new ArrayList<>();
        if (genre.getRomance() == 'Y') selectedGenres.add("로맨스");
        if (genre.getComedy() == 'Y') selectedGenres.add("코미디");
        if (genre.getThriller() == 'Y') selectedGenres.add("스릴러");
        if (genre.getAnimation() == 'Y') selectedGenres.add("애니메이션");
        if (genre.getAction() == 'Y') selectedGenres.add("액션");
        if (genre.getDrama() == 'Y') selectedGenres.add("드라마");
        if (genre.getHorror() == 'Y') selectedGenres.add("공포");
        if (genre.getFantasy() == 'Y') selectedGenres.add("판타지");

        String ottPlatformRaw = genre.getOttPlatform();
        List<String> selectedOtts = new ArrayList<>();
        if (ottPlatformRaw != null && !ottPlatformRaw.isEmpty()) {
            selectedOtts = Arrays.stream(ottPlatformRaw.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();
        }

        List<String> encodedGenres = selectedGenres.stream()
                .map(g -> URLEncoder.encode(g, StandardCharsets.UTF_8))
                .toList();
        List<String> encodedOtts = selectedOtts.stream()
                .map(o -> URLEncoder.encode(o, StandardCharsets.UTF_8))
                .toList();
        String encodedTitle = URLEncoder.encode(selectedTitle, StandardCharsets.UTF_8);

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString("https://good-vlmd.onrender.com/recommend/selected");

        for (String genreStr : encodedGenres) {
            builder.queryParam("user_genre", genreStr);
        }
        for (String ott : encodedOtts) {
            builder.queryParam("user_ott", ott);
        }

        builder.queryParam("prefer_new", genre.isLatestYear())
                .queryParam("selected_title", encodedTitle)
                .queryParam("total_needed", 5);

        URI uri = builder.build(true).toUri();

        System.out.println("✅ [선택 추천 URI] " + URLDecoder.decode(uri.toString(), StandardCharsets.UTF_8));

        Mono<String> responseMono = webClient.get().uri(uri).retrieve().bodyToMono(String.class);

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
                contentList.add(content);
            }

            return contentList;

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}