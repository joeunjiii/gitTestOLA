package com.smhrd.olaPJ.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserViewController {


    //회원가입 호출
    @GetMapping("/signup")
    public String signup() {
        System.out.println("회원가입 페이지 요청됨: signup.html 반환");
        return "signup";
    }


    //로그인 호출
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String FirstPage() {
        System.out.println("main page 반환 : login.html");
        return "main";  // main.html 반환
    }

    //장르 페이지 호출
    @GetMapping("/genre")
    public String genre() {
        System.out.println("회원가입 -> 장르페이지 반환 : genre.html");
        return "select_genre";
    }



}
