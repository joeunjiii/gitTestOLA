package com.smhrd.olaPJ.controller;


import ch.qos.logback.core.util.COWArrayList;
import com.smhrd.olaPJ.domain.User;
import com.smhrd.olaPJ.dto.UserResponse;
import com.smhrd.olaPJ.repository.UserRepository;
import com.smhrd.olaPJ.service.UserDetailService;
import com.smhrd.olaPJ.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;
import java.util.Optional;


//사용자 정보 관리 컨트롤러
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserDetailService userDetailService;
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserDetailService userDetailService, UserRepository userRepository, UserService userService) {
        this.userDetailService = userDetailService;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/current")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("인증되지 않은 사용자 요청 발생!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String userName = authentication.getName();
        System.out.println("현재 로그인된 사용자: " + userName);

        Optional<User> optionalUser = userRepository.findByUsername(userName);

        if(optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        User user = optionalUser.get();
        Integer genreSelected = user.getGenreSelected();

        return ResponseEntity.ok(new UserResponse(userName, genreSelected));
    }
}

