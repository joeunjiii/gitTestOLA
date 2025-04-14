package com.smhrd.olaPJ.repository;


import com.smhrd.olaPJ.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowee(String follower, String followee);
    void deleteByFollowerAndFollowee(String follower, String followee);
    List<Follow> findByFollower(String follower);
    List<Follow> findByFollowee(String followee);
}

