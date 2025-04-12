package com.smhrd.olaPJ.repository;

import com.smhrd.olaPJ.domain.Favorite;
import com.smhrd.olaPJ.domain.Content;
import com.smhrd.olaPJ.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    // ✅ 유저가 해당 콘텐츠를 찜했는지 확인
    Optional<Favorite> findByUserAndContent(User user, Content content);

    // ✅ 유저가 찜한 전체 콘텐츠 리스트
    List<Favorite> findByUser(User user);

    // ✅ 콘텐츠별 찜 많은 순 (랭킹용)
    @Query("SELECT f.content.title, COUNT(f) FROM Favorite f GROUP BY f.content.title ORDER BY COUNT(f) DESC")
    List<Object[]> findRankingByFavoriteCount();
}
