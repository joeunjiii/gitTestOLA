//package com.smhrd.olaPJ.repository;
//
//import com.smhrd.olaPJ.domain.PostLike;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
//    Optional<PostLike> findBPostSeqAndUserId(Long postSeq, Long userId);
//    int countByPostSeq(Long postSeq);
//}
