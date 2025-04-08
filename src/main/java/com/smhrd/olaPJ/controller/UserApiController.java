package com.smhrd.olaPJ.controller;

import com.smhrd.olaPJ.dto.AddUserRequest;
import com.smhrd.olaPJ.service.UserDetailService;
import com.smhrd.olaPJ.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RequiredArgsConstructor
@Controller
public class UserApiController {

    private final UserService userService;
    private final UserDetailService userDetailService;

    //회원가입 호출
    @PostMapping("/user")
    public String signUp(AddUserRequest request, HttpServletRequest httpRequest) {
        userService.save(request); //회원가입 메서드를 호출

        UserDetails userDetails = userDetailService.loadUserByUsername(request.getUsername());
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        //세션에 인증정보 직접 저장
        SecurityContextHolder.getContext().setAuthentication(auth);
        httpRequest.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        //회원가입 완료 -> 장르 페이지 이동
        return "redirect:/genre";
    }

    //로그아웃 호출
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login"; //로그아웃 버튼 -> 로그인 페이지 이동
    }





}
