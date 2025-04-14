package com.smhrd.olaPJ.controller;

import com.smhrd.olaPJ.domain.User;
import com.smhrd.olaPJ.dto.FollowDto;
import com.smhrd.olaPJ.repository.UserRepository;
import com.smhrd.olaPJ.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
@Slf4j
public class FollowController {

    private final FollowService followService;
    private final UserRepository userRepository;

    // ✅ 1. 팔로우 요청
    @PostMapping
    public ResponseEntity<Void> follow(@RequestBody FollowDto dto, Authentication auth) {
        String username = auth.getName();
        String followerId = userRepository.findByUsername(username)
                .map(User::getUserId)
                .orElseThrow(() -> new RuntimeException("❌ 로그인 사용자 ID 조회 실패"));

        String followeeId = dto.getFollowee();

        log.info("📌 [팔로우 요청] {} → {}", followerId, followeeId);
        followService.follow(followerId, followeeId);

        return ResponseEntity.ok().build();
    }


    // ✅ 2. 언팔로우 요청
    @DeleteMapping
    public ResponseEntity<Void> unfollow(@RequestBody FollowDto dto, Authentication auth) {
        String username = auth.getName();
        String followerId = userRepository.findByUsername(username)
                .map(User::getUserId)
                .orElseThrow(() -> new RuntimeException("❌ 로그인 사용자 ID 조회 실패"));

        String followeeId = dto.getFollowee();

        log.info("📌 [언팔로우 요청] {} → {}", followerId, followeeId);
        followService.unfollow(followerId, followeeId);

        return ResponseEntity.ok().build();
    }


    // ✅ 3. 내가 팔로우한 사용자 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<FollowDto>> getMyFollowingList(Authentication auth) {
        String userId = auth.getName();

        log.info("📌 [팔로잉 목록 조회] userId: {}", userId);
        return ResponseEntity.ok(followService.getFollowingList(userId));
    }

    // ✅ 4. 팔로우 상태 확인
    @GetMapping("/status")
    public ResponseEntity<Boolean> checkFollowStatus(@RequestParam String followee, Authentication auth) {
        String username = auth.getName();
        String myUserId = userRepository.findByUsername(username)
                .map(User::getUserId)
                .orElseThrow(() -> new RuntimeException("❌ USER_ID 조회 실패"));

        boolean isFollowing = followService.isFollowing(myUserId, followee);
        return ResponseEntity.ok(isFollowing);
    }

    // ✅ 5. 팔로워/팔로잉 유저 목록 (간단 정보)
    @GetMapping("/users")
    public ResponseEntity<Map<String, List<Map<String, String>>>> getFollowUsers(Authentication auth) {
        String username = auth.getName(); // username
        String userId = userRepository.findByUsername(username)
                .map(User::getUserId)
                .orElseThrow(() -> new RuntimeException("❌ 사용자 ID 조회 실패"));

        log.info("📌 [팔로워/팔로잉 유저 조회] 요청자 USER_ID: {}", userId);

        List<Map<String, String>> followers = followService.getFollowerUsers(userId);
        List<Map<String, String>> followings = followService.getFollowingUsers(userId);

        return ResponseEntity.ok(Map.of(
                "followers", followers,
                "followings", followings
        ));
    }



    // ✅ 6. 팔로워/팔로잉 수 비동기 조회용
    @GetMapping("/counts")
    public ResponseEntity<Map<String, Long>> getFollowCounts(Authentication auth) {
        String username = auth.getName();
        String userId = userRepository.findByUsername(username)
                .map(User::getUserId)
                .orElseThrow(() -> new RuntimeException("❌ 사용자 ID 조회 실패"));

        long followerCount = followService.countFollowers(userId);
        long followingCount = followService.countFollowings(userId);

        log.info("📌 [팔로워/팔로잉 수 조회] userId: {}, follower: {}, following: {}", userId, followerCount, followingCount);

        return ResponseEntity.ok(Map.of(
                "followerCount", followerCount,
                "followingCount", followingCount
        ));
    }
}
