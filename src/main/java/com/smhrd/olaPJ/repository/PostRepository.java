package com.smhrd.olaPJ.repository;

import com.smhrd.olaPJ.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.AbstractDocument;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
}
