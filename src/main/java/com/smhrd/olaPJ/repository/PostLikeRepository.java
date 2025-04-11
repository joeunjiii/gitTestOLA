package com.smhrd.olaPJ.repository;

import com.smhrd.olaPJ.domain.Post;
import com.smhrd.olaPJ.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    int countByPostSeq(Long postSeq);

    Optional<PostLike> findByPostSeqAndUserId(Long postSeq, String userId);

    List<PostLike> findByUserId(String userId);

    @Query("SELECT p FROM PostLike pl JOIN Post p ON p.postSeq = pl.postSeq LEFT JOIN FETCH p.content WHERE pl.userId = :userId")
    List<Post> findLikedPostsWithContentByUserId(@Param("userId") String userId);



}

