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
        //객체 생성 후 리턴해야함
        User user = User.builder()
                .userId(dto.getUserId())
                //비밀번호 암호화
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build();

        userRepository.save(user);
    }


}
