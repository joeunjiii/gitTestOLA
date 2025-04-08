package com.smhrd.olaPJ.repository;

import com.smhrd.olaPJ.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface GenreRepository extends JpaRepository<Genre, Long> {


    Optional<Genre> findByUserId(String userId);

    boolean existsByUserId(String userId);
}
