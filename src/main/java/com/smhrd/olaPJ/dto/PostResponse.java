package com.smhrd.olaPJ.dto;

import com.smhrd.olaPJ.domain.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {


    private String nickname;
    private Long postSeq;
    private String userId;
    private String postTitle;
    private String postContent;
    private String postFile1;
    private String postFile2;
    private String postFile3;
    private LocalDateTime createdAt;


    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .postSeq(post.getPostSeq())
                .userId(post.getUserId())
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .postFile1(post.getPostFile1())
                .postFile2(post.getPostFile2())
                .postFile3(post.getPostFile3())
                .createdAt(post.getCreatedAt())
                .build();
    }

    public static PostResponse fromWithNickname(Post post, String nickname) {
        return PostResponse.builder()
                .nickname(nickname)
                .postSeq(post.getPostSeq())
                .userId(post.getUserId())
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .postFile1(post.getPostFile1())
                .postFile2(post.getPostFile2())
                .postFile3(post.getPostFile3())
                .createdAt(post.getCreatedAt())
                .build();
    }
}