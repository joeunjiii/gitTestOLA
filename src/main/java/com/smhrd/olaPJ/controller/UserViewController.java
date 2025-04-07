package com.smhrd.olaPJ.controller;

import com.smhrd.olaPJ.repository.UserRepository;
import com.smhrd.olaPJ.service.AiServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;


@Controller
public class UserViewController {


    private final UserRepository userRepository;

    public UserViewController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
        return "login";  // http://localhost:8083/ 으로 실행 시 login 화면이 출력됨
    }

    //장르 페이지 호출
    @GetMapping("/genre")
    public String genre() {
        System.out.println("회원가입 -> 장르페이지 반환 : genre.html");
        return "select_genre";
    }
    @Autowired
    private AiServiceClient aiServiceClient;
    //메인 페이지 호출
    @GetMapping("/main")
    public String showMainPage(Model model) {
        System.out.println("main.html 반환");
        List<Map<String, Object>> results = aiServiceClient.getRecommendation();
        model.addAttribute("results", results);
        return "main"; //메인페이지 반환
    }

    @GetMapping("/redirect")
    public String redirectCheck() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }
        return "redirect_check";
    }

}