package com.smhrd.olaPJ.service;

import com.smhrd.olaPJ.domain.Favorite;
import com.smhrd.olaPJ.domain.User;
import com.smhrd.olaPJ.domain.Content;
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
}
