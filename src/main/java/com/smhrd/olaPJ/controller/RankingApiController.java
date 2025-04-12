package com.smhrd.olaPJ.controller;

import com.smhrd.olaPJ.dto.RankingDto;
import com.smhrd.olaPJ.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
public class RankingApiController {

    private final RankingService rankingService;

    @GetMapping("/reviews")
    public List<RankingDto> reviewRanking() {
        return rankingService.getReviewRanking();
    }

    @GetMapping("/ratings")
    public List<RankingDto> ratingRanking() {
        return rankingService.getRatingRanking();
    }

    @GetMapping("/favorites")
    public List<RankingDto> favoriteRanking() {
        return rankingService.getFavoriteRanking();
    }

    @GetMapping("/review-top3")
    public List<RankingDto> getReviewTop3() {
        return rankingService.getTop3ReviewRanking();
    }

}
