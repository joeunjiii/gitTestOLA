package com.smhrd.olaPJ.repository;

import com.smhrd.olaPJ.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findByTitleContainingIgnoreCase(String keyword);

    // 평점 높은 순
    @Query("SELECT c.title, c.rating FROM Content c ORDER BY c.rating DESC")
    List<Object[]> findRankingByRating();
    Optional<Content> findByTitle(String title);

}
