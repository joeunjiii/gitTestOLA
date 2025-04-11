package com.smhrd.olaPJ.controller;

import com.smhrd.olaPJ.domain.Comment;
import com.smhrd.olaPJ.dto.CommentRequest;
import com.smhrd.olaPJ.dto.CommentResponse;
import com.smhrd.olaPJ.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 목록 조회
    @GetMapping("/{postSeq}")
    public List<CommentResponse> getComments(@PathVariable Long postSeq) {
        return commentService.getCommentByPostSeq(postSeq);
    }

    // 댓글 작성
    @PostMapping
    public Comment writeComment(@RequestBody CommentRequest dto,
                                @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.saveComment(dto, userDetails.getUsername());
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        String userId = userDetails.getUsername();
        commentService.deleteComment(id, userId);
        return ResponseEntity.ok().build();

    }

    // 댓글 좋아요
    @PostMapping("/{id}/like")
    public void likeComment(@PathVariable Long id) {
        commentService.likeComment(id);
    }
}
