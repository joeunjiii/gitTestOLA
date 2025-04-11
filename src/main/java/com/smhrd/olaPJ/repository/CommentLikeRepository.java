package com.smhrd.olaPJ.repository;

import com.smhrd.olaPJ.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentIdAndUserId(Long commentId, String userId);



    long countByCommentId(Long commentId);
}
