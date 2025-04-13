package com.smhrd.olaPJ.controller;

import com.smhrd.olaPJ.domain.Content;
import com.smhrd.olaPJ.domain.Post;
import com.smhrd.olaPJ.domain.User;
import com.smhrd.olaPJ.dto.PostResponse;
import com.smhrd.olaPJ.repository.UserRepository;
import com.smhrd.olaPJ.service.ContentService;
import com.smhrd.olaPJ.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
public class ContentApiController {

    private final ContentService contentService;
    private final PostService postService;
    private final UserRepository userRepository;

    @GetMapping("/detail")
    public ResponseEntity<?> getContentDetail(@RequestParam String title) {
        Content content = contentService.getContentByTitle(title);
        List<Post> posts = postService.getPostsByContentId(content.getId());

        // 2. 좋아요 수, 닉네임 포함된 PostResponse 리스트 조회 (서비스에서 처리)
        List<PostResponse> reviews = postService.getPostResponsesByContentId(content.getId());

        Map<String, Object> result = new HashMap<>();
        result.put("content", content);
        result.put("reviews", reviews);

        return ResponseEntity.ok(result);
    }
}
