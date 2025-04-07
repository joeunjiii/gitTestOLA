package com.smhrd.olaPJ.dto;

import lombok.Getter;

@Getter

public class UserResponse {
    private final String username;
    private final Integer genreSelected;

    public UserResponse(String username, Integer genreSelected) {
        this.username = username;
        this.genreSelected = genreSelected;
    }

}
