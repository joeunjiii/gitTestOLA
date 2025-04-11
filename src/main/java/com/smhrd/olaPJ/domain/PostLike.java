package com.smhrd.olaPJ.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TB_POST_LIKE",  uniqueConstraints = @UniqueConstraint(columnNames = {"postSeq", "userId"}))
public class PostLike {


    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="LIKE_SEQ")
    private Long likeSeq;

    @Column(name = "POST_SEQ", nullable = false)
    private Long postSeq;

    @Column(name = "USER_ID", length = 50, nullable = false)
    private String userId;

    @Column(name = "CREATED_AT")
    private LocalDateTime created_at = LocalDateTime.now();

    public PostLike(Long postSeq, String userId) {
        this.postSeq = postSeq;
        this.userId = userId;
    }

}
