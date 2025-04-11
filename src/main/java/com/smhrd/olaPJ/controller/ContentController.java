package com.smhrd.olaPJ.controller;

import com.smhrd.olaPJ.domain.Content;
import com.smhrd.olaPJ.domain.Post;
import com.smhrd.olaPJ.service.ContentService;
import com.smhrd.olaPJ.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;
    private final PostService postService;

    // ✅ /reviewDetail?title=OOO 요청 시 처리
    @GetMapping("/reviewDetail")
    public String showReviewDetail(@RequestParam("title") String title, Model model) {

        // 1. 콘텐츠 정보 조회
        Content content = contentService.getContentByTitle(title);

        // 2. 해당 콘텐츠에 대한 리뷰 목록
        List<Post> postList = postService.getPostsByContentId(content.getId());

        // 3. 모델에 담기
        model.addAttribute("content", content);
        model.addAttribute("posts", postList);

        return "reviewDetail"; // templates/reviewDetail.html 렌더링
    }
}
