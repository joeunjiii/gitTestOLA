package com.smhrd.olaPJ.service;


import com.smhrd.olaPJ.domain.User;
import com.smhrd.olaPJ.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    //사용자 이름(username)으로 사용자의 정보를 가져오는 메서드
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
