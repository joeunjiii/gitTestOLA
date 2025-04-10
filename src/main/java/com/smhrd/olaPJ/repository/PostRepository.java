package com.smhrd.olaPJ.repository;

import com.smhrd.olaPJ.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.AbstractDocument;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByPostTitleContaining(String keyword);

    Optional<Post> findByPostTitle(String postTitle);

    Optional<Post> findByPostTitleAndUserId(String postTitle, String username);
}
