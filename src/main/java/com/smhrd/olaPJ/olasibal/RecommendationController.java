package com.smhrd.olaPJ.olasibal;

import com.smhrd.olaPJ.olasibal.Content;
import com.smhrd.olaPJ.olasibal.RecommendationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    @PostMapping("/recommend")
    public List<Content> recommend(
            @RequestParam List<String> ott,
            @RequestParam List<String> genres,
            @RequestParam(defaultValue = "10") int size) {
        return service.recommend(ott, genres, size);
    }
}
