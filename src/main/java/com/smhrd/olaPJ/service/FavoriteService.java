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

    // ✅ 찜 토글 기능
    @Transactional
    public boolean toggleFavorite(String username, Long contentId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자 정보 없음"));

        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("콘텐츠 정보 없음"));

        Optional<Favorite> existing = favoriteRepository.findByUserAndContent(user, content);

        if (existing.isPresent()) {
            favoriteRepository.delete(existing.get());
            return false;
        } else {
            Favorite favorite = Favorite.builder()
                    .user(user)
                    .content(content)
                    .build();
            favoriteRepository.save(favorite);
            return true;
        }
    }

    // ✅ 로그인 유저의 찜 목록 조회
    public List<Favorite> getFavoritesByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자 정보 없음"));

        return favoriteRepository.findByUser(user);
    }

    // ✅ 유사 유저들의 찜 목록 조회
    public List<FavoriteResponse> getFavoritesBySimilarUsers(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자 정보 없음"));
        String userId = user.getUserId();

        List<String> similarUserIds = genreService.findUsersWithSimilarGenres(userId);

        if (similarUserIds == null || similarUserIds.isEmpty()) {
            return List.of();
        }

        List<Favorite> favorites = favoriteRepository.findAllByUser_UserIdIn(similarUserIds);

        return favorites.stream().map(fav ->
                FavoriteResponse.builder()
                        .contentId(fav.getContent().getId())
                        .title(fav.getContent().getTitle())
                        .posterImg(fav.getContent().getPosterImg())
                        .nickname(fav.getUser().getNickname())
                        .userId(fav.getUser().getUserId()) // ✅ 추가된 부분
                        .build()
        ).toList();
    }
}
