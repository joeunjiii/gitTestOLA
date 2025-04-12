package com.smhrd.olaPJ.service;

import com.smhrd.olaPJ.dto.RankingDto;
import com.smhrd.olaPJ.repository.ContentRepository;
import com.smhrd.olaPJ.repository.FavoriteRepository;
import com.smhrd.olaPJ.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final PostRepository postRepository;
    private final ContentRepository contentRepository;
    private final FavoriteRepository favoriteRepository;

    public List<RankingDto> getReviewRanking() {
        return contentRepository.findRankingByReviewCountDto()
                .stream().limit(100)
                .toList();
    }

    public List<RankingDto> getRatingRanking() {
        return contentRepository.findRankingByRatingDto()
                .stream().limit(100)
                .toList();
    }

    public List<RankingDto> getFavoriteRanking() {
        return contentRepository.findRankingByFavoriteCountDto()
                .stream().limit(100)
                .toList();
    }

    public List<RankingDto> getTop3ReviewRanking() {
        return getReviewRanking().stream().limit(3).toList();
    }
}
