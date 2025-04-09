package com.smhrd.olaPJ.repository;

import com.smhrd.olaPJ.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findByTitleContainingIgnoreCase(String keyword);
}
