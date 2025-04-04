package com.smhrd.olaPJ.dto;

import lombok.Getter;

@Getter
public class UserResponse {
    private final String username;

    public UserResponse(String username) {
        this.username = username;
    }

}
