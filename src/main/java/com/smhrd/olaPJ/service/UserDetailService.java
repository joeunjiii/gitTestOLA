package com.smhrd.olaPJ.service;

import com.smhrd.olaPJ.domain.User;
import com.smhrd.olaPJ.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    // 사용자 이름(username)으로 사용자의 정보를 가져오는 메서드
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(" 로그인 시도: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error(" 사용자 없음: {}", username);
                    return new UsernameNotFoundException("User not found with ID: " + username);
                });

        log.info(" 사용자 찾음: {}", user.getUsername());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }




}