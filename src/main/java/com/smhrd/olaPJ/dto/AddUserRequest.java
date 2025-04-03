package com.smhrd.olaPJ.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddUserRequest {
    private String userId;
    private String password;
    private String phone;
    private String nickname;
    private String profileImg;


    // 모든 필드를 포함한 생성자 추가
    public AddUserRequest(String userId, String password, String phone, String nickname, String profileImg) {
        this.userId = userId;
        this.password = password;
        this.phone = phone;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }


}
