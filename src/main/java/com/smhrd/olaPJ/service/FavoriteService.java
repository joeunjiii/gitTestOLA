package com.smhrd.olaPJ.service;

import com.smhrd.olaPJ.domain.Favorite;
import com.smhrd.olaPJ.domain.User;
import com.smhrd.olaPJ.domain.Content;
import com.smhrd.olaPJ.dto.FavoriteResponse;
import com.smhrd.olaPJ.repository.FavoriteRepository;
import com.smhrd.olaPJ.repository.UserRepository;
import com.smhrd.olaPJ.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;
    private final GenreService genreService;

    // ✅ 찜 토글 기능 (찜 추가 또는 해제)
    @Transactional
    public boolean toggleFavorite(String username, Long contentId) {
        // 유저 정보 가져오기
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자 정보 없음"));

        // 콘텐츠 정보 가져오기
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("콘텐츠 정보 없음"));

        // 기존 찜 여부 확인
        Optional<Favorite> existing = favoriteRepository.findByUserAndContent(user, content);

        if (existing.isPresent()) {
            // 이미 찜 → 해제
            favoriteRepository.delete(existing.get());
            return false; // 찜 해제됨
        } else {
            // 찜 추가
            Favorite favorite = Favorite.builder()
                    .user(user)
                    .content(content)
                    .build();
            favoriteRepository.save(favorite);
            return true; // 찜 추가됨
        }
    }

    // ✅ 유저가 찜한 콘텐츠 리스트 조회
    public List<Favorite> getFavoritesByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자 정보 없음"));

        return favoriteRepository.findByUser(user);
    }

    public List<FavoriteResponse> getFavoritesBySimilarUsers(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자 정보 없음"));
        String userId = user.getUserId();

        // ✅ 장르 기준 비슷한 유저 찾기
        List<String> similarUserIds = genreService.findUsersWithSimilarGenres(userId);

        if (similarUserIds == null || similarUserIds.isEmpty()) {
            return List.of();
        }

        // ✅ 유사 유저들의 찜 정보 가져오기
        List<Favorite> favorites = favoriteRepository.findAllByUser_UserIdIn(similarUserIds);

        // ✅ DTO로 변환
        return favorites.stream().map(fav ->
                FavoriteResponse.builder()
                        .contentId(fav.getContent().getId())
                        .title(fav.getContent().getTitle())
                        .posterImg(fav.getContent().getPosterImg())
                        .nickname(fav.getUser().getNickname())  // 여기 핵심!
                        .build()
        ).toList();
    }



}
