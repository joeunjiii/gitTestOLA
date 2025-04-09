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

    @Column(name = "POSTER_IMG")
    private String posterImg;


    public String getTitle() {
        return title;
    }

    public String getPosterImg() {
        return posterImg;
    }
}
