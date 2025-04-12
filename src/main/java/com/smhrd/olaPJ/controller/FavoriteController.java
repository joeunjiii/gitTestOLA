package com.smhrd.olaPJ.controller;

import com.smhrd.olaPJ.domain.Content;
import com.smhrd.olaPJ.domain.Favorite;
import com.smhrd.olaPJ.dto.FavoriteResponse;
import com.smhrd.olaPJ.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    // ✅ 찜 추가 or 해제 (토글)
    @PostMapping("/toggle")
    public ResponseEntity<?> toggleFavorite(@RequestBody Map<String, Long> body,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        Long contentId = body.get("contentId");

        boolean isFavorite = favoriteService.toggleFavorite(userDetails.getUsername(), contentId);

        return ResponseEntity.ok(Map.of("favorite", isFavorite));
    }

    // ✅ 찜 목록 조회 (마이페이지 등)
    @GetMapping("/list")
    public ResponseEntity<?> getFavoriteList(@AuthenticationPrincipal UserDetails userDetails) {
        List<Favorite> favorites = favoriteService.getFavoritesByUser(userDetails.getUsername());

        // 선택: DTO 변환
        List<FavoriteResponse> responseList = favorites.stream().map(fav ->
                FavoriteResponse.builder()
                        .contentId(fav.getContent().getId())
                        .title(fav.getContent().getTitle())
                        .posterImg(fav.getContent().getPosterImg())
                        .build()
        ).collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/similar-users")
    public ResponseEntity<?> getRecommendationsFromSimilarUsers(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            String username = userDetails.getUsername();
            List<FavoriteResponse> recommendations = favoriteService.getFavoritesBySimilarUsers(username);
            return ResponseEntity.ok(recommendations);
        } catch (Exception e) {
            e.printStackTrace(); // ✅ 콘솔에 오류 출력
            return ResponseEntity.status(500).body(Map.of("error", "서버 오류 발생"));
        }
    }


}
