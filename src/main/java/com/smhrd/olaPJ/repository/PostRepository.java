package com.smhrd.olaPJ.repository;

import com.smhrd.olaPJ.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 기본 CRUD 가능
}
