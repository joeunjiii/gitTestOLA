package com.smhrd.olaPJ.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowDto {

    private Long followSeq;

    private String follower;   // 나
    private String followee;   // 상대방
    private LocalDateTime followedAt;

    // 상태 확인용 (선택)
    private Boolean isFollowing;

}
