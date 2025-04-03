package com.smhrd.olaPJ.service;

import com.smhrd.olaPJ.domain.User;
import com.smhrd.olaPJ.dto.AddUserRequest;
import com.smhrd.olaPJ.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void save(AddUserRequest dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }



        // User 객체 생성
        User user = User.builder()
                .username(dto.getUsername())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .phone(dto.getPhone())
                .nickname(dto.getNickname())
                .profileImg(null) // 기본값 설정
                .role("USER") // 기본적으로 일반 사용자로 설정
                .build();

        userRepository.save(user);
        System.out.println("회원가입 성공함! 생성된 userId: " + user.getUserId());



    }

}
