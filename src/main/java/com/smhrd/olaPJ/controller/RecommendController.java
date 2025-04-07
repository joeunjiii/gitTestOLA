package com.smhrd.olaPJ.controller;

import com.smhrd.olaPJ.service.AiServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class RecommendController {

    @Autowired
    private AiServiceClient aiServiceClient;

    @GetMapping("/main")
    public String showRecommendationResult(Model model) {
        List<Map<String, Object>> results = aiServiceClient.getRecommendation();
        model.addAttribute("results", results);
        return "main"; // templates/result.html 로 이동
    }
}

