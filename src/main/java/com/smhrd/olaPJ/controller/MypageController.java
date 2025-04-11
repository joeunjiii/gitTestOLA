package com.smhrd.olaPJ.controller;

import com.smhrd.olaPJ.domain.Post;
import com.smhrd.olaPJ.domain.User;
import com.smhrd.olaPJ.repository.PostLikeRepository;
import com.smhrd.olaPJ.repository.PostRepository;
import com.smhrd.olaPJ.repository.UserRepository;
import com.smhrd.olaPJ.service.GenreService;
import com.smhrd.olaPJ.service.PostService;
import com.smhrd.olaPJ.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MypageController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserService userService;
    private final GenreService genreService;


    @GetMapping("/mypage")
    public String mypage(Authentication auth, Model model) {
        String username = auth.getName();

        // 유저 정보
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        String userId = user.getUserId();

       List<Post> myPosts = postRepository.findAllByUserIdWithContent(userId);
        List<Post> likedReviews = postLikeRepository.findLikedPostsWithContentByUserId(userId);

        model.addAttribute("myPosts", myPosts);
        model.addAttribute("likedReviews", likedReviews);
        model.addAttribute("user", user);

        return "mypage";
    }

    @GetMapping("/mypage/edit")
    public String editProfilePage(Principal principal, Model model) {
        String username = principal.getName();
        User user = userService.findByUsername(username); // 예시

        model.addAttribute("nickname", user.getNickname());
        model.addAttribute("introduce", user.getIntroduce());
        model.addAttribute("profileImg", user.getProfileImg());

        List<String> genres = genreService.getGenresByUserId(user.getUserId());
        model.addAttribute("selectedGenres", genres != null ? genres : List.of()); //null 방지

        return "user-info"; // Thymeleaf 템플릿 이름
    }

    @PostMapping("/mypage/update")
    public String updateProfile(@RequestParam String nickname,
                                @RequestParam String introduce,
                                @RequestParam(value = "genres", required = false) List<String> genres,
                                @RequestParam(value = "profileImg", required = false) MultipartFile profileImg,
                                Principal principal) {

        String userName = principal.getName();

        userService.updateProfile(userName, nickname, introduce, profileImg);
        String userId = userService.findUserIdByUsername(userName);
        genreService.updateGenres(userId, genres);

        return "redirect:/mypage";
    }

}
