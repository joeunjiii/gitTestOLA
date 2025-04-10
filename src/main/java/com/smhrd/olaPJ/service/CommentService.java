package com.smhrd.olaPJ.service;

import com.smhrd.olaPJ.domain.Comment;
import com.smhrd.olaPJ.domain.User;
import com.smhrd.olaPJ.dto.CommentRequest;
import com.smhrd.olaPJ.dto.CommentResponse;
import com.smhrd.olaPJ.repository.CommentRepository;
import com.smhrd.olaPJ.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public List<CommentResponse> getCommentByPostSeq(Long postSeq) {
        return commentRepository.findByPostSeqOrderByCreatedAtAsc(postSeq)
                .stream()
                .map(comment -> {
                    User user = userRepository.findByUserId(comment.getUserId())
                            .orElse(null); // null 처리도 고려

                    return CommentResponse.builder()
                            .id(comment.getId())
                            .content(comment.getContent())
                            .createdAt(comment.getCreatedAt().format(formatter))
                            .likes(comment.getLikes())
                            .userId(comment.getUserId())
                            .username(user != null ? user.getUsername() : "알 수 없음") //
                            .build();
                })
                .collect(Collectors.toList());
    }



    public Comment saveComment(CommentRequest dto, String username) {
       User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저 정보를 찾을 수 없습니다."));
        Comment.CommentBuilder builder = Comment.builder()
                .postSeq(dto.getPostSeq())
                .content(dto.getContent())
                .userId(user.getUserId())
                .likes(0)
                .createdAt(LocalDateTime.now());


                if(dto.getSuperSeq() != null && dto.getSuperSeq() != 0) {
                    Comment parent = commentRepository.findById(dto.getSuperSeq())
                            .orElseThrow(() -> new RuntimeException("댓글이 없습니다."));
                    builder.superComment(parent);
                }

        return commentRepository.save(builder.build());
    }

    @Transactional
    public void deleteComment(Long id, String userId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        if (!comment.getUserId().equals(userId)) {
            throw new RuntimeException("본인의 댓글만 삭제할 수 있습니다.");
        }
        commentRepository.delete(comment);
    }

    @Transactional
    public void likeComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        comment.setLikes(comment.getLikes() + 1);
    }
}
