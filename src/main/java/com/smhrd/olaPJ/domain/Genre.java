package com.smhrd.olaPJ.domain;


import jakarta.persistence.*;
import lombok.*;


//장르 저장

@Table(name="TB_GENRE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Genre {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GENRE_SEQ")
    private Long id;

    @Column(name = "USER_ID", nullable = false, length = 50)
    private String userId;

    @Column(name = "ROMANCE", nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private char romance = 'N';

    @Column(name = "COMEDY", nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private char comedy = 'N';

    @Column(name = "THRILLER", nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private char thriller = 'N';

    @Column(name = "ANIMATION", nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private char animation = 'N';

    @Column(name = "ACTION", nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private char action = 'N';

    @Column(name = "DRAMA", nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private char drama = 'N';

    @Column(name = "HORROR", nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private char horror = 'N';

    @Column(name = "FANTASY", nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private char fantasy = 'N';

    @Column(name = "OTT_PLATFORM",length = 50)
    private String ottPlatform;

    @Column(name = "LATEST_YEAR", columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean latestYear;






}