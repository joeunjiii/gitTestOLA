package com.smhrd.olaPJ.repository;

import com.smhrd.olaPJ.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {


    boolean existsByUserId(String userId);
}
