package com.smhrd.olaPJ.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_FOLLOW")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FOLLOW_SEQ")
    private Long followSeq;

    @Column(name = "FOLLOWER", nullable = false)
    private String follower; // 사용자 ID (나)

    @Column(name = "FOLLOWEE", nullable = false)
    private String followee; // 대상 사용자 ID (상대방)

    @Column(name = "FOLLOWED_AT")
    private LocalDateTime followedAt;
}

