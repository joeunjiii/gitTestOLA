package com.smhrd.olaPJ.controller;

import com.smhrd.olaPJ.dto.GenreRequest;
import com.smhrd.olaPJ.service.GenreService;
import lombok.RequiredArgsConstructor;
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

        return "redirect:/redirect_check";
    }

}
