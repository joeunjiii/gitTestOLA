package com.smhrd.olaPJ.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Table(name="TB_USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity

//유저정보
public class User implements UserDetails {

    @Id
    @Column(name = "USER_ID", length = 50, updatable = false, nullable = false)
    private String userId = UUID.randomUUID().toString();

    @Column(name = "USER_NAME",length = 50, nullable = false, unique = true)
    private String username; //이녀석이 id가 될 상인가


    @Column(name="USER_PW", length = 200, nullable = false)
    private String password;

    @Column(name="USER_PHONE", length = 20, nullable = false)
    private String phone;

    @Column(name="USER_NICK", length = 30, nullable = false, unique = true)
    private String nickname;

    @Column(name="PROFILE_IMG", length = 1000)
    private String profileImg;

    @Column(name="USER_ROLE", length = 10, nullable = false)
    private String role;

    @Column(name="JOINED_AT", nullable = false)
    private LocalDateTime joinedAt;

    @Setter
    @Column(name="GENRE_SELECTED", nullable = false)
    private int genreSelected;



    @Builder
    public User(String username, String password, String phone, String nickname, String profileImg, String role) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.nickname = nickname;
        this.profileImg = profileImg; //프로필 이미지
        this.role = role; // 회원구분
        this.joinedAt = LocalDateTime.now(); // 현재 시간 설정

    }

    //회원가입 시 자동으로 현재 시간을 저장하도록 설정
    @PrePersist
    protected void onCreate() {
        this.joinedAt = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    //사용자의 id 반환 (고유값을 반환하기 때문에 어떤값을 설정하면 좋을지 생각필요)
    @Override
    public String getUsername() {
        return username;
    }

    //사용자의 패스워드 반환
    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부 반환(아래부터 필요여부에 따라서 삭제 or 유지)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //패스워드 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return true;
    }


    public boolean isGenreSelected() {
        return this.genreSelected == 1;
    }
}
