package com.smhrd.olaPJ.service;

import com.smhrd.olaPJ.domain.Follow;
import com.smhrd.olaPJ.domain.User;
import com.smhrd.olaPJ.dto.FollowDto;
import com.smhrd.olaPJ.repository.FollowRepository;
import com.smhrd.olaPJ.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

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

    // 3. 내가 팔로우한 유저 관계 정보 조회
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

    // 4. 팔로잉 유저 정보 (닉네임, userId)
    public List<Map<String, String>> getFollowingUsers(String myUserId) {
        List<String> userIds = followRepository.findByFollower(myUserId).stream()
                .map(Follow::getFollowee)
                .collect(Collectors.toList());

        return userRepository.findAllById(userIds).stream()
                .map(u -> Map.of(
                        "userId", u.getUserId(),
                        "nickname", u.getNickname(),
                        "profileImg", u.getProfileImg() != null ? u.getProfileImg() : "/images/default_profile.jpg"
                ))
                .collect(Collectors.toList());
    }

    // 5. 팔로워 유저 정보 (닉네임, userId)
    public List<Map<String, String>> getFollowerUsers(String myUserId) {
        List<String> userIds = followRepository.findByFollowee(myUserId).stream()
                .map(Follow::getFollower)
                .collect(Collectors.toList());

        return userRepository.findAllById(userIds).stream()
                .map(u -> Map.of(
                        "userId", u.getUserId(),
                        "nickname", u.getNickname(),
                        "profileImg", u.getProfileImg() != null ? u.getProfileImg() : "/images/default_profile.jpg",
                        "isFollowing", String.valueOf(isFollowing(myUserId, u.getUserId()))
                ))
                .collect(Collectors.toList());
    }


    // 6. 팔로우 여부 확인
    public boolean isFollowing(String followerUserId, String followeeUserId) {
        return followRepository.existsByFollowerAndFollowee(followerUserId, followeeUserId);
    }

    // 7. 자기 자신 팔로우 방지
    private void validateSelfFollow(String followerUserId, String followeeUserId) {
        if (followerUserId.equals(followeeUserId)) {
            throw new IllegalArgumentException("⚠️ 자기 자신을 팔로우할 수 없습니다.");
        }
    }

    public long countFollowers(String userId) {
        return followRepository.countFollowers(userId);
    }

    public long countFollowings(String userId) {
        return followRepository.countFollowings(userId);
    }
}
