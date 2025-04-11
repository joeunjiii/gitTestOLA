package com.smhrd.olaPJ.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_FAVORITE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAV_SEQ")
    private Long favSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_SEQ")
    private Post post;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
