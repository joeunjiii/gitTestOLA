package com.smhrd.olaPJ.controller;

import com.smhrd.olaPJ.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



//사용자 정보 관리 컨트롤러
@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/current")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("인증되지 않은 사용자 요청 발생!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        System.out.println("현재 로그인된 사용자: " + authentication.getName());
        return ResponseEntity.ok(new UserResponse(authentication.getName()));
    }
}

