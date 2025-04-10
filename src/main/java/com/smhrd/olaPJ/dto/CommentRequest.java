package com.smhrd.olaPJ.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    private Long postSeq;
    private String content;
    private String userId;
    private Long superSeq;
}
