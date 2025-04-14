package com.smhrd.olaPJ.repository;

import com.smhrd.olaPJ.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface GenreRepository extends JpaRepository<Genre, Long> {


    Optional<Genre> findByUserId(String userId);

    boolean existsByUserId(String userId);



    @Query("SELECT g.selectedTitle FROM Genre g WHERE g.userId = :userId")
    List<String> findGenreNamesByUserId(@Param("userId") String userId);

    @Query("""
    SELECT g.userId
    FROM Genre g
    WHERE g.userId != :userId
    ORDER BY 
        (CASE WHEN g.romance = 'Y' AND :romance = 'Y' THEN 1 ELSE 0 END +
        CASE WHEN g.comedy = 'Y' AND :comedy = 'Y' THEN 1 ELSE 0 END +
        CASE WHEN g.thriller = 'Y' AND :thriller = 'Y' THEN 1 ELSE 0 END +
        CASE WHEN g.animation = 'Y' AND :animation = 'Y' THEN 1 ELSE 0 END +
        CASE WHEN g.action = 'Y' AND :action = 'Y' THEN 1 ELSE 0 END +
        CASE WHEN g.drama = 'Y' AND :drama = 'Y' THEN 1 ELSE 0 END +
        CASE WHEN g.horror = 'Y' AND :horror = 'Y' THEN 1 ELSE 0 END +
        CASE WHEN g.fantasy = 'Y' AND :fantasy = 'Y' THEN 1 ELSE 0 END
        ) DESC
""")
    List<String> findUsersWithSimilarGenres(
            @Param("userId") String userId,
            @Param("romance") String romance,
            @Param("comedy") String comedy,
            @Param("thriller") String thriller,
            @Param("animation") String animation,
            @Param("action") String action,
            @Param("drama") String drama,
            @Param("horror") String horror,
            @Param("fantasy") String fantasy
    );



}
