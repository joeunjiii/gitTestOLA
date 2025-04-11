package com.smhrd.olaPJ.repository;

import com.smhrd.olaPJ.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    int countByPostSeq(Long postSeq);

    Optional<PostLike> findByPostSeqAndUserId(Long postSeq, String userId);
}
