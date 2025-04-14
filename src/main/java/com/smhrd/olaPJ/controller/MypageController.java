package com.smhrd.olaPJ.controller;

import com.smhrd.olaPJ.domain.Favorite;
import com.smhrd.olaPJ.domain.Post;
import com.smhrd.olaPJ.domain.User;
import com.smhrd.olaPJ.dto.FavoriteResponse;
import com.smhrd.olaPJ.repository.FavoriteRepository;
import com.smhrd.olaPJ.repository.PostLikeRepository;
import com.smhrd.olaPJ.repository.PostRepository;
import com.smhrd.olaPJ.repository.UserRepository;
import com.smhrd.olaPJ.service.FavoriteService;
import com.smhrd.olaPJ.service.FollowService;
import com.smhrd.olaPJ.service.GenreService;
import com.smhrd.olaPJ.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MypageController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserService userService;
    private final GenreService genreService;
    private final FavoriteRepository favoriteRepository;
    private final FollowService followService; // ✅ 팔로우 서비스 추가

    @GetMapping("/mypage")
    public String mypage(Authentication auth, Model model) {
        String username = auth.getName();

        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return "redirect:/login";

        String userId = user.getUserId();

        // ✅ 마이페이지 정보들
        List<Post> myPosts = postRepository.findAllByUserIdWithContent(userId);
        List<Post> likedReviews = postLikeRepository.findLikedPostsWithContentByUserId(userId);
        List<Favorite> favorites = favoriteRepository.findByUser(user);
        List<FavoriteResponse> favoriteContents = favorites.stream()
                .map(fav -> FavoriteResponse.builder()
                        .contentId(fav.getContent().getId())
                        .title(fav.getContent().getTitle())
                        .posterImg(fav.getContent().getPosterImg())
                        .build())
                .toList();

        // ✅ 팔로워/팔로잉 수 추가
        long followerCount = followService.countFollowers(userId);
        long followingCount = followService.countFollowings(userId);

        model.addAttribute("user", user);
        model.addAttribute("myPosts", myPosts);
        model.addAttribute("likedReviews", likedReviews);
        model.addAttribute("favoriteContents", favoriteContents);
        model.addAttribute("followerCount", followerCount);    // ✅
        model.addAttribute("followingCount", followingCount);  // ✅

        return "mypage";
    }

    @GetMapping("/mypage/edit")
    public String editProfilePage(Principal principal, Model model) {
        String username = principal.getName();
        User user = userService.findByUsername(username);

        model.addAttribute("user", user);
        List<String> genres = genreService.getGenresByUserId(user.getUserId());
        model.addAttribute("selectedGenres", genres != null ? genres : List.of());

        return "user-info";
    }

    @PostMapping("/mypage/update")
    public String updateProfile(@RequestParam(required = false) String nickname,
                                @RequestParam(required = false) String introduce,
                                @RequestParam(value = "genres", required = false) List<String> genres,
                                @RequestParam(value = "profileImg", required = false) MultipartFile profileImg,
                                Principal principal) {

        String username = principal.getName();

        List<String> allGenres = List.of("romance", "comedy", "thriller", "animation", "action", "drama", "horror", "fantasy");

        Map<String, String> parsedGenres = new HashMap<>();
        for (String genre : allGenres) {
            // 로그 추가
            System.out.println("장르 [" + genre + "] 선택됨? " + (genres != null && genres.contains(genre)));
            parsedGenres.put(genre, genres != null && genres.contains(genre) ? "Y" : "N");
        }

        userService.updateProfile(username, nickname, introduce, profileImg, parsedGenres);
        return "redirect:/mypage";
    }




}
