package com.smhrd.olaPJ.repository;

import com.smhrd.olaPJ.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByPostTitleContaining(String keyword);

    // 리뷰 많은 순
    @Query("SELECT p.postTitle, COUNT(p) as reviewCount FROM Post p GROUP BY p.postTitle ORDER BY reviewCount DESC")
    List<Object[]> findRankingByReviewCount();


    Optional<Post> findByPostSeq(Long postSeq);
    Optional<Post> findByPostTitleAndUserId(String postTitle, String username);

    Post findByPostSeq(Long postSeq);

    // 특정 콘텐츠에 대한 전체 리뷰(Post) 목록
    List<Post> findByContent_IdOrderByCreatedAtDesc(Long contentId);
}
