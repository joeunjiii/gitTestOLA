package com.smhrd.olaPJ.service;

import com.smhrd.olaPJ.domain.User;
import com.smhrd.olaPJ.dto.AddUserRequest;
import com.smhrd.olaPJ.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(AddUserRequest dto) {
        // 중복 닉네임 체크
        if (userRepository.findByNickname(dto.getNickname()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        // User 객체 생성 및 저장
        User user = User.builder()
                .userId(dto.getUserId())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .phone(dto.getPhone())
                .nickname(dto.getNickname())
                .profileImg(dto.getProfileImg())
                .role("USER") // 기본적으로 일반 사용자로 설정
                .build();

        userRepository.save(user);


    }
}
