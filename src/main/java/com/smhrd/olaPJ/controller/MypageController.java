package com.smhrd.olaPJ.controller;

import com.smhrd.olaPJ.domain.Post;
import com.smhrd.olaPJ.domain.User;
import com.smhrd.olaPJ.repository.PostLikeRepository;
import com.smhrd.olaPJ.repository.PostRepository;
import com.smhrd.olaPJ.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MypageController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

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
}
