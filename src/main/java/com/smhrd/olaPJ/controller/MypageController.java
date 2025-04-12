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
    private final FavoriteService favoriteService;


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
        // ✅ 추가: 찜한 콘텐츠 가져오기
        List<Favorite> favorites = favoriteRepository.findByUser(user);
        List<FavoriteResponse> favoriteContents = favorites.stream().map(fav -> FavoriteResponse.builder()
                .contentId(fav.getContent().getId())
                .title(fav.getContent().getTitle())
                .posterImg(fav.getContent().getPosterImg())
                .build()).toList();

        model.addAttribute("myPosts", myPosts);
        model.addAttribute("likedReviews", likedReviews);
        model.addAttribute("favoriteContents", favoriteContents); // ✅ 추가
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
    public String updateProfile(@RequestParam(required = false) String nickname,
                                @RequestParam(required = false) String introduce,
                                @RequestParam Map<String, String> genres,
                                @RequestParam(value = "profileImg", required = false) MultipartFile profileImg,
                                Principal principal) {

        String username = principal.getName(); // 현재 로그인된 사용자 이름
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 닉네임 수정
        if (nickname != null && !nickname.isBlank()) {
            user.setNickname(nickname);
        }

        // 소개글 수정
        if (introduce != null && !introduce.isBlank()) {
            user.setIntroduce(introduce);
        }

        // 이미지 수정
        if (profileImg != null && !profileImg.isEmpty()) {
            try {
                String fileName = saveFile(profileImg); // 실제 저장 경로 반환
                user.setProfileImg(fileName);
            } catch (IOException e) {
                throw new RuntimeException("이미지 저장 실패", e);
            }
        }

        // 장르 업데이트 - genres 맵에서 "genres[xxx]" 형식 파싱
        Map<String, String> parsedGenres = new HashMap<>();
        genres.forEach((key, value) -> {
            if (key.startsWith("genres[")) {
                String genreKey = key.substring(7, key.length() - 1); // 예: genres[romance] → romance
                parsedGenres.put(genreKey, value);
            }
        });

        if (!parsedGenres.isEmpty()) {
            genreService.updateGenres(user.getUserId(), parsedGenres);
        }

        return "redirect:/mypage";
    }

    private String saveFile(MultipartFile file) throws IOException {
        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File dest = new File(uploadDir + fileName);
        file.transferTo(dest);

        return fileName;
    }


}
