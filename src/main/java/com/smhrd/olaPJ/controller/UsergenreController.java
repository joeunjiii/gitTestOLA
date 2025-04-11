package com.smhrd.olaPJ.controller;

import com.smhrd.olaPJ.dto.GenreRequest;
import com.smhrd.olaPJ.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genre")
public class UsergenreController {
    private final GenreService genreService;

    @PostMapping("/save")
    public String saveGenre(@RequestBody GenreRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName(); //현재 로그인된 유저id 가져옴

        genreService.saveGenre(request, userId);

        return "saved";
    }

    @PostMapping("/save-ott")
    public ResponseEntity<?> saveOtt(@RequestBody GenreRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName(); // 현재 로그인된 유저 ID 가져옴
        genreService.saveOttPlatform(request, userId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/save-title")
    public ResponseEntity<?> saveSelectedTitle(@RequestBody GenreRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName(); // 로그인된 유저 ID

        // 인자 순서 수정: (username, selectedTitle)
        genreService.saveSelectedTitle(userId, request.getSelectedTitle());

        return ResponseEntity.ok().build();
    }









}