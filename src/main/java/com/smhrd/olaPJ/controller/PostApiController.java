package com.smhrd.olaPJ.controller;

import com.smhrd.olaPJ.dto.ContentRequest;
import com.smhrd.olaPJ.repository.PostRepository;
import com.smhrd.olaPJ.service.ContentService;
import com.smhrd.olaPJ.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostApiController {

    private final PostRepository postRepository;
    private final PostService postService;
    private final ContentService contentService;

//    // ✅ 전체 게시글 조회
//    @GetMapping
//    public List<PostResponse> getAllPosts() {
//        return postService.getAllPosts();
//    }
//
//    // ✅ 게시글 작성 (setter 문제 방지용 수정 버전)
//    @PostMapping
//    public Post createPost(@RequestBody Post post) {
//        Post newPost = Post.builder()
//                .userId(post.getUserId())
//                .postTitle(post.getPostTitle())
//                .postContent(post.getPostContent())
//                .postFile1(post.getPostFile1())
//                .postFile2(post.getPostFile2())
//                .postFile3(post.getPostFile3())
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .build();
//
//        return postRepository.save(newPost);
//    }

    //컨텐츠 키워드 검색
    @GetMapping("/search")
    @ResponseBody
    public List<ContentRequest> searchContent(@RequestParam("keyword") String keyword) {
        return contentService.searchByTitle(keyword);
    }

//    //리뷰 좋아요
//    @PostMapping("/posts/{id}/like")
//    public ResponseEntity<?> likePost(@PathVariable Long postSeq,
//                                      @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            int updateLikes = postService.likePost(postSeq, userDetails.getUsername());
//            return ResponseEntity.ok().body("Update like count:" +updateLikes);
//            } catch (Exception e) {
//            return ResponseEntity.badRequest().body("에러:" + e.getMessage());
//        }
//    }







}
