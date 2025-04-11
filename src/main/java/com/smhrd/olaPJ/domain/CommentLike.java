package com.smhrd.olaPJ.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TB_COMMENT_LIKE", uniqueConstraints = @UniqueConstraint(columnNames = {"commentId", "userId"}))
public class CommentLike {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name = "COMMENT_ID", nullable = false)
    private Long commentId;

    @Column(name ="USER_ID", length = 50, nullable = false)
    private String userId;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();

    public CommentLike(Long commentId, String userId) {
        this.commentId = commentId;
        this.userId = userId;
    }

}
