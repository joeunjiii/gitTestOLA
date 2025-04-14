package com.smhrd.olaPJ.service;

import com.smhrd.olaPJ.domain.Follow;
import com.smhrd.olaPJ.dto.FollowDto;
import com.smhrd.olaPJ.repository.FollowRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    // 1. 팔로우
    public void follow(String followerUserId, String followeeUserId) {
        validateSelfFollow(followerUserId, followeeUserId);

        if (!isFollowing(followerUserId, followeeUserId)) {
            Follow follow = Follow.builder()
                    .follower(followerUserId)
                    .followee(followeeUserId)
                    .followedAt(LocalDateTime.now())
                    .build();
            followRepository.save(follow);
        }
    }

    // 2. 언팔로우
    @Transactional
    public void unfollow(String followerUserId, String followeeUserId) {
        followRepository.deleteByFollowerAndFollowee(followerUserId, followeeUserId);
    }

    // 3. 내가 팔로우한 유저 목록 조회
    public List<FollowDto> getFollowingList(String followerUserId) {
        return followRepository.findByFollower(followerUserId).stream()
                .map(f -> FollowDto.builder()
                        .followSeq(f.getFollowSeq())
                        .follower(f.getFollower())
                        .followee(f.getFollowee())
                        .followedAt(f.getFollowedAt())
                        .build())
                .collect(Collectors.toList());
    }

    // 4. 팔로우 여부 확인
    public boolean isFollowing(String followerUserId, String followeeUserId) {
        return followRepository.existsByFollowerAndFollowee(followerUserId, followeeUserId);
    }

    // 5. 자기 자신 팔로우 방지
    private void validateSelfFollow(String followerUserId, String followeeUserId) {
        if (followerUserId.equals(followeeUserId)) {
            throw new IllegalArgumentException("⚠️ 자기 자신을 팔로우할 수 없습니다.");
        }
    }
}
