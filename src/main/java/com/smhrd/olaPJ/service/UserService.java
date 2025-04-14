package com.smhrd.olaPJ.service;

import com.smhrd.olaPJ.domain.User;
import com.smhrd.olaPJ.dto.AddUserRequest;
import com.smhrd.olaPJ.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final String uploadDir = System.getProperty("user.dir") + "/uploads/";
    private final GenreService genreService;


    @Transactional
    public void save(AddUserRequest dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        String defaultProfileImg = "/images/default_profile.jpg";



        // User 객체 생성
        User user = User.builder()
                .username(dto.getUsername())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .phone(dto.getPhone())
                .nickname(dto.getNickname())
                .profileImg(defaultProfileImg) // 기본값 설정
                .role("USER") // 기본적으로 일반 사용자로 설정
                .build();

        userRepository.save(user);
        System.out.println("회원가입 성공함! 생성된 userId: " + user.getUserId());



    }

    @Transactional
    public void updateProfile(String username, String nickname, String introduce, MultipartFile profileImg, Map<String, String> parsedGenres) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setNickname(nickname);
            user.setIntroduce(introduce != null ? introduce : ""); // null 방지

            if (profileImg != null && !profileImg.isEmpty()) {
                try {
                    String savedFileName = saveFile(profileImg, uploadDir);
                    user.setProfileImg(savedFileName);
                } catch (IOException e) {
                    throw new RuntimeException("파일 저장 실패", e);
                }
            }

            // ✅ Genre 저장 로직 추가
            if (parsedGenres != null && !parsedGenres.isEmpty()) {
                genreService.updateGenres(user.getUserId(), parsedGenres);
            }

            userRepository.save(user);
        }
    }


    private String saveFile(MultipartFile file, String uploadDir) throws IOException {
        System.out.println("▶ 업로드 경로: " + uploadDir);
        System.out.println("▶ 원본 파일명: " + file.getOriginalFilename());

        File dir = new File(uploadDir);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("디렉토리 생성 실패");
        }

        String uuid = UUID.randomUUID().toString();
        String originalName = file.getOriginalFilename();
        String savedName = uuid + "_" + originalName;

        File dest = new File(uploadDir + savedName);
        file.transferTo(dest);
        return savedName;
    }




    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("유저 X"));
    }




}
