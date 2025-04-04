package com.smhrd.olaPJ.controller;

import com.smhrd.olaPJ.domain.Post;
import com.smhrd.olaPJ.dto.PostResponse;
import com.smhrd.olaPJ.repository.PostRepository;
import com.smhrd.olaPJ.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

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
}
