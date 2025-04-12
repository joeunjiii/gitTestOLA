package com.smhrd.olaPJ.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteResponse {
    private Long contentId;
    private String title;
    private String posterImg;
    private String nickname;
}
