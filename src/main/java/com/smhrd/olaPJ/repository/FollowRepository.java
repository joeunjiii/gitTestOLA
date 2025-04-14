package com.smhrd.olaPJ.repository;


import com.smhrd.olaPJ.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowee(String follower, String followee);
    void deleteByFollowerAndFollowee(String follower, String followee);
    List<Follow> findByFollower(String follower);
    List<Follow> findByFollowee(String followee);

    // ✅ 팔로워 수 조회
    @Query("SELECT COUNT(f) FROM Follow f WHERE f.followee = :userId")
    long countFollowers(@Param("userId") String userId);

    // ✅ 팔로잉 수 조회
    @Query("SELECT COUNT(f) FROM Follow f WHERE f.follower = :userId")
    long countFollowings(@Param("userId") String userId);
}

