package com.smhrd.olaPJ.olasibal;

import com.smhrd.olaPJ.olasibal.RecommendationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class WebController {

    private final RecommendationService service;

    public WebController(RecommendationService service) {
        this.service = service;
    }

    @GetMapping("/recommend")
    public String recommendPage(@RequestParam(required = false) List<String> ott,
                                @RequestParam(required = false) List<String> genres,
                                Model model) {
        if (ott == null) ott = List.of("넷플릭스","쿠팡플레이","티빙");
        if (genres == null) genres = List.of("드라마","스릴러","코미디","로맨스");
        model.addAttribute("recommendations", service.recommend(ott, genres, 10));
        return "recommend";
    }
}
