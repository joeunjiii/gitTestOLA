package com.smhrd.olaPJ.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//체크박스 장르 선택에서 받을 정보 / 메인페이지 최신년도/감독/주연

@Getter
@Setter
@Data
public class GenreRequest {
    private String romance;
    private String comedy;
    private String thriller;
    private String animation;
    private String action;
    private String drama;
    private String horror;
    private String fantasy;
    private List<String> ottPlatform = new ArrayList<>();;
    private String director;
    private String characters;
    private boolean latestYear;
}
