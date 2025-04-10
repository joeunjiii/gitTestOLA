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

    // ✅ 리뷰 랭킹 (리뷰 많은 순)
    public List<RankingDto> getReviewRanking() {
        return postRepository.findRankingByReviewCount().stream()
                .map(obj -> {
                    String title = (String) obj[0];
                    Long value = (Long) obj[1];

                    return contentRepository.findByTitle(title)
                            .map(content -> new RankingDto(
                                    title,
                                    value,
                                    content.getPosterImg(),
                                    content.getContentsGenre(),
                                    content.getReleaseYear()
                            ))
                            .orElse(new RankingDto(title, value, null, "장르없음", 0));
                })
                .collect(Collectors.toList());
    }

    // ✅ 평점 랭킹 (평점 높은 순)
    public List<RankingDto> getRatingRanking() {
        return contentRepository.findRankingByRating().stream()
                .map(obj -> {
                    String title = (String) obj[0];
                    Double value = ((Number) obj[1]).doubleValue();

                    return contentRepository.findByTitle(title)
                            .map(content -> new RankingDto(
                                    title,
                                    value,
                                    content.getPosterImg(),
                                    content.getContentsGenre(),
                                    content.getReleaseYear()
                            ))
                            .orElse(new RankingDto(title, value, null, "장르없음", 0));
                })
                .collect(Collectors.toList());
    }

    // ✅ 찜 랭킹 (찜 많은 순)
    public List<RankingDto> getFavoriteRanking() {
        return favoriteRepository.findRankingByFavoriteCount().stream()
                .map(obj -> {
                    String title = (String) obj[0];
                    Long value = (Long) obj[1];

                    return contentRepository.findByTitle(title)
                            .map(content -> new RankingDto(
                                    title,
                                    value,
                                    content.getPosterImg(),
                                    content.getContentsGenre(),
                                    content.getReleaseYear()
                            ))
                            .orElse(new RankingDto(title, value, null, "장르없음", 0));
                })
                .collect(Collectors.toList());
    }

    public List<RankingDto> getTop3ReviewRanking() {
        return getReviewRanking().stream()
                .limit(3)
                .collect(Collectors.toList());
    }
}
