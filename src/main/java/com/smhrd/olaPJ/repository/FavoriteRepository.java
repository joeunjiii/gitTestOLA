package com.smhrd.olaPJ.repository;

import com.smhrd.olaPJ.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Comment, Long> {

    // 찜 많은 순
    @Query("SELECT p.postTitle, COUNT(f) FROM Favorite f JOIN f.post p GROUP BY p.postTitle ORDER BY COUNT(f) DESC")
    List<Object[]> findRankingByFavoriteCount();
}
