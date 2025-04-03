package com.smhrd.olaPJ.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    //로그인 호출
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //회원가입 호출
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

}
