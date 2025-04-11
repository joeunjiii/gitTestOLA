package com.smhrd.olaPJ.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "TB_COMMENT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CMT_SEQ")
    private Long id;

    @Column(name = "POST_SEQ")
    private Long postSeq;

    @Column(name = "CMT_CONTENT", length = 900)
    private String content;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "CMT_LIKES")
    private int likes;

    @Column(name = "USER_ID")
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPER_SEQ")
    private Comment superComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_SEQ", insertable = false, updatable = false)
    private Post post;
}
