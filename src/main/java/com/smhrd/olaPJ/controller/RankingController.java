package com.smhrd.olaPJ.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RankingController {

    @GetMapping("/ranking-page")
    public String showRankingPage() {
        return "ranking-page"; // templates/ranking-page.html로 이동
    }
}
