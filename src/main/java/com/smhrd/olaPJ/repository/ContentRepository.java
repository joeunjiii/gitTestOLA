package com.smhrd.olaPJ.repository;

import com.smhrd.olaPJ.domain.Content;
import com.smhrd.olaPJ.dto.RankingDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {

    List<Content> findByTitleContainingIgnoreCase(String keyword);
    Optional<Content> findByTitle(String title);
    List<Content> findByIdIn(List<Long> ids);

    // ✅ 평점 높은 순 (DTO 직접 반환)
    @Query("""
        SELECT new com.smhrd.olaPJ.dto.RankingDto(
            c.title,
            c.rating,
            c.posterImg,
            c.contentsGenre,
            c.releaseYear
        )
        FROM Content c
        ORDER BY c.rating DESC
    """)
    List<RankingDto> findRankingByRatingDto();

    // ✅ 리뷰 많은 순 (Post 기준 JOIN 필요)
    @Query("""
        SELECT new com.smhrd.olaPJ.dto.RankingDto(
            c.title,
            COUNT(p),
            c.posterImg,
            c.contentsGenre,
            c.releaseYear
        )
        FROM Post p
        JOIN p.content c
        GROUP BY c.title, c.posterImg, c.contentsGenre, c.releaseYear
        ORDER BY COUNT(p) DESC
    """)
    List<RankingDto> findRankingByReviewCountDto();

    // ✅ 찜 많은 순 (Favorite 기준 JOIN 필요)
    @Query("""
        SELECT new com.smhrd.olaPJ.dto.RankingDto(
            c.title,
            COUNT(f),
            c.posterImg,
            c.contentsGenre,
            c.releaseYear
        )
        FROM Favorite f
        JOIN f.content c
        GROUP BY c.title, c.posterImg, c.contentsGenre, c.releaseYear
        ORDER BY COUNT(f) DESC
    """)
    List<RankingDto> findRankingByFavoriteCountDto();
}