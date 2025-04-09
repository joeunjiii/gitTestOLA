package com.smhrd.olaPJ.controller;

import com.smhrd.olaPJ.repository.UserRepository;
import com.smhrd.olaPJ.service.AiServiceClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
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
    public String login(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate(); //세션 강제 종료!
        return "login";
    }

    @GetMapping("/")
    public String FirstPage() {
        System.out.println("main page 반환 : login.html");
        return "main";  // http://localhost:8083/ 으로 실행 시 login 화면이 출력됨
    }

    //장르 페이지 호출
    @GetMapping("/genre")
    public String genre() {
        System.out.println("회원가입 -> 장르페이지 반환 : genre.html");
        return "select_genre";
    }

    @Autowired
    private AiServiceClient aiServiceClient;

    @GetMapping("/main")
    public String showMainPage(Model model, Principal principal) {
        String username = principal.getName(); // 현재 로그인 유저 이름

        // 기본 추천
        List<Map<String, Object>> basicRecommendations = aiServiceClient.getBasicRecommendation(username);
        model.addAttribute("basicResults", basicRecommendations);

        // ✅ 선택 콘텐츠 하드코딩 테스트 ("더 글로리" 기준)
        String selectedTitle = "폭싹 속았수다";
        List<Map<String, Object>> selectedRecommendations = aiServiceClient.getSelectedRecommendation(username, selectedTitle);
        model.addAttribute("selectedResults", selectedRecommendations);

        return "main"; // templates/main.html
    }

    @GetMapping("/viewport")
    public String viewport() {
        return "viewport"; // viewport.html
    }



    @GetMapping("/redirect")
    public String redirectCheck(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }

        String username = auth.getName();
        var userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            var user = userOptional.get();
            boolean selected = user.getGenreSelected() == 1;
            model.addAttribute("genreSelected", selected); // 뷰로 상태 전달
            return "redirect_check"; //분기 결정 페이지
        }

        return "redirect:/login"; // 사용자 없으면 로그인으로
    }

    @GetMapping("/review")
    public String reviewPage() {
        return "review";
    }


}