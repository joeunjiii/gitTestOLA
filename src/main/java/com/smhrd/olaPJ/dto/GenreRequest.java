package com.smhrd.olaPJ.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenreRequest {
    private String romance;
    private String comedy;
    private String thriller;
    private String animation;
    private String action;
    private String drama;
    private String horror;
    private String fantasy;
    private String ottPlatform;
    private String director;
    private String characters;
    private boolean latestYear;
}
