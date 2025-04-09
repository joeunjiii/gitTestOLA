package com.smhrd.olaPJ.controller;

import com.smhrd.olaPJ.domain.Post;
import com.smhrd.olaPJ.dto.ContentRequest;
import com.smhrd.olaPJ.dto.PostResponse;
import com.smhrd.olaPJ.repository.PostRepository;
import com.smhrd.olaPJ.repository.UserRepository;
import com.smhrd.olaPJ.service.ContentService;
import com.smhrd.olaPJ.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;
    private final ContentService contentService;
    private final UserRepository userRepository;

    // ✅ 전체 게시글 조회
    @GetMapping
    public List<PostResponse> getAllPosts() {
        return postService.getAllPosts();
    }

    // ✅ 게시글 작성 (setter 문제 방지용 수정 버전)
    @PostMapping
    public Post createPost(@RequestBody Post post) {
        Post newPost = Post.builder()
                .userId(post.getUserId())
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .postFile1(post.getPostFile1())
                .postFile2(post.getPostFile2())
                .postFile3(post.getPostFile3())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return postRepository.save(newPost);
    }

    //컨텐츠 키워드 검색
    @GetMapping("/search")
    @ResponseBody
    public List<ContentRequest> searchContent(@RequestParam("keyword") String keyword) {
        return contentService.searchByTitle(keyword);
    }

    //사진
    @PostMapping("/uploadReview")
    public String uploadReview(@RequestParam("postTitle") String postTitle,
                               @RequestParam("file1") MultipartFile file1,
                               @RequestParam("file2") MultipartFile file2,
                               @RequestParam("file3") MultipartFile file3,
                              Authentication authentication) {

        String username = authentication.getName(); // 로그인 시 사용한 아이디 (USER_NAME)
        try {
            postService.uploadReview(postTitle, file1, file2, file3, username);
            System.out.println("Post Review uploaded");
            return "redirect:/review";
        }catch (IOException e){
            e.printStackTrace();
            return "error";
        }
    }





}
