package com.smhrd.olaPJ.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {
    private String password;
    private String username;

    public String getUserId() {
        return getUsername();
    }
}
