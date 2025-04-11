package com.smhrd.olaPJ.repository;

import com.smhrd.olaPJ.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostSeqOrderByCreatedAtAsc(Long postSeq);

    List<Comment> getCommentsByPostSeq(Long postSeq);

    void deleteByIdAndUserId(Long id, String userId);

    List<Comment> findByPost_PostSeqOrderByCreatedAtAsc(Long postSeq);

}
