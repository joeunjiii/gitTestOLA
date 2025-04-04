package com.smhrd.olaPJ.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddUserRequest {
    private String username;
    private String password;
    private String phone;
    private String nickname;//회원가입에서 받을 회원 정보
 }