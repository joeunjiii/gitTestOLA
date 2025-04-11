package com.smhrd.olaPJ.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CONTENTS_FIN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTENTS_SEQ")
    private Long id;

    @Column(name = "CONTENTS_TITLE")
    private String title;

    @Column(name = "CONTENTS_SYNOPSIS")
    private String synopsis;

    @Column(name = "POSTER_IMG")
    private String posterImg;

    @Column(name = "RATING")
    private int rating;

    @Column(name = "CONTENTS_GENRE")
    private String contentsGenre;

    @Column(name = "RELEASE_YEAR")
    private int releaseYear;

    @Column(name = "AGE_RATING")
    private String ageRating;

    @Column(name = "DIRECTOR")
    private String director;

    @Column(name = "CAST")
    private String cast;

    @Column(name = "OTT")
    private String ott;
}
