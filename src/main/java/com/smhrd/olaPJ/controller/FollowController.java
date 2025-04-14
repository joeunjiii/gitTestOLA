package com.smhrd.olaPJ.controller;

import com.smhrd.olaPJ.dto.FollowDto;
import com.smhrd.olaPJ.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
@Slf4j
public class FollowController {

    private final FollowService followService;

    // 1. 팔로우 요청 (followee는 USER_ID)
    @PostMapping
    public ResponseEntity<Void> follow(@RequestBody FollowDto dto, Authentication auth) {
        String followerUsername = auth.getName();             // 로그인한 사용자의 USER_NAME
        String followeeUserId = dto.getFollowee();            // 요청으로 받은 대상 USER_ID

        log.info("📌 [팔로우 요청] {} → {}", followerUsername, followeeUserId);
        followService.follow(followerUsername, followeeUserId);

        return ResponseEntity.ok().build();
    }

    // 2. 언팔로우 요청
    @DeleteMapping
    public ResponseEntity<Void> unfollow(@RequestBody FollowDto dto, Authentication auth) {
        String followerUsername = auth.getName();
        String followeeUserId = dto.getFollowee();

        log.info("📌 [언팔로우 요청] {} → {}", followerUsername, followeeUserId);
        followService.unfollow(followerUsername, followeeUserId);

        return ResponseEntity.ok().build();
    }

    // 3. 내가 팔로우한 사용자 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<FollowDto>> getFollowingList(Authentication auth) {
        String followerUsername = auth.getName();

        log.info("📌 [팔로우 목록 조회] 요청자: {}", followerUsername);
        return ResponseEntity.ok(followService.getFollowingList(followerUsername));
    }

    // 4. 특정 사용자에 대한 팔로우 여부 확인
    @GetMapping("/status")
    public ResponseEntity<Boolean> isFollowing(@RequestParam String followee, Authentication auth) {
        String followerUsername = auth.getName();
        boolean result = followService.isFollowing(followerUsername, followee);

        log.info("📌 [팔로우 상태 확인] {} → {} = {}", followerUsername, followee, result);
        return ResponseEntity.ok(result);
    }
}
