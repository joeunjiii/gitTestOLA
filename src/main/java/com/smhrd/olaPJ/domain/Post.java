package com.smhrd.olaPJ.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_POST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_SEQ")
    private Long postSeq;

    @Column(name = "USER_ID", nullable = false, length = 50)
    private String userId;

    @Column(name = "POST_TITLE", length = 600, nullable = false)
    private String postTitle;

    @Column(name = "POST_CONTENT", columnDefinition = "TEXT")
    private String postContent;

    @Column(name = "POST_FILE1", length = 1000)
    private String postFile1;

    @Column(name = "POST_FILE2", length = 1000)
    private String postFile2;

    @Column(name = "POST_FILE3", length = 1000)
    private String postFile3;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

}
