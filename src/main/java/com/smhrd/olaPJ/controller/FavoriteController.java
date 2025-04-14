package com.smhrd.olaPJ.controller;

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

    // ✅ 찜 추가 or 해제
    @PostMapping("/toggle")
    public ResponseEntity<Map<String, Boolean>> toggleFavorite(@RequestBody Map<String, Long> body,
                                                               @AuthenticationPrincipal UserDetails userDetails) {
        Long contentId = body.get("contentId");
        String userName = userDetails.getUsername();  // USER_NAME

        boolean isFavorite = favoriteService.toggleFavorite(userName, contentId);
        return ResponseEntity.ok(Map.of("favorite", isFavorite));
    }

    // ✅ 현재 유저의 찜 목록 (마이페이지)
    @GetMapping("/list")
    public ResponseEntity<List<FavoriteResponse>> getFavoriteList(@AuthenticationPrincipal UserDetails userDetails) {
        String userName = userDetails.getUsername();

        List<FavoriteResponse> responseList = favoriteService.getFavoritesByUser(userName).stream()
                .map(fav -> FavoriteResponse.builder()
                        .contentId(fav.getContent().getId())
                        .title(fav.getContent().getTitle())
                        .posterImg(fav.getContent().getPosterImg())
                        .nickname(fav.getUser().getNickname())
                        .userId(fav.getUser().getUserId()) // ✅ userId도 포함
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    // ✅ 비슷한 유저의 찜 콘텐츠 리스트 반환
    @GetMapping("/similar-users")
    public ResponseEntity<?> getRecommendationsFromSimilarUsers(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            String userName = userDetails.getUsername();
            List<FavoriteResponse> recommendations = favoriteService.getFavoritesBySimilarUsers(userName);
            return ResponseEntity.ok(recommendations);
        } catch (Exception e) {
            System.err.println("❌ 유사 유저 찜 콘텐츠 조회 중 오류:");
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "비슷한 유저 콘텐츠를 불러오는 중 오류 발생"));
        }
    }
}
