package com.smhrd.olaPJ.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String content;
    private String createdAt;
    private int likes;
    private String userId;
    private String username;
    private Long superSeq;
}
