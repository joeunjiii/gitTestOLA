package com.smhrd.olaPJ.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankingDto {
    private String title;       // 콘텐츠 제목
    private Object value;       // 리뷰 수, 평점, 찜 수 등
    private String posterImg;
    private String genre;
    private int releaseYear;
}

