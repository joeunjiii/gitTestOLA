package com.smhrd.olaPJ.service;

import com.smhrd.olaPJ.domain.Content;
import com.smhrd.olaPJ.domain.Post;
import com.smhrd.olaPJ.domain.PostLike;
import com.smhrd.olaPJ.domain.User;
import com.smhrd.olaPJ.dto.PostResponse;
import com.smhrd.olaPJ.repository.ContentRepository;
import com.smhrd.olaPJ.repository.PostLikeRepository;
import com.smhrd.olaPJ.repository.PostRepository;
import com.smhrd.olaPJ.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final ContentRepository contentRepository;


    public List<Post> getPostsByContentId(Long contentId) {
        return postRepository.findByContent_IdOrderByCreatedAtDesc(contentId);
    }


    public Long uploadReview(Long contentId, String postTitle, MultipartFile file1, MultipartFile file2, MultipartFile file3, String username) throws IOException {
        // 1. 실제 유저 찾기
        Optional<User> optionalUser = userRepository.findByUsername(username);
        Optional<Content> optionalContent = contentRepository.findById(contentId);
        if (optionalUser.isEmpty() || optionalContent.isEmpty()) {
            throw new IllegalArgumentException("해당 유저가 존재하지 않습니다: " + username);
        }
        User user = optionalUser.get();
        Content content = optionalContent.get();

        // 2. 파일 저장 (루트/uploads 폴더 사용)
        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        String fileName1 = saveFile(file1, uploadDir);
        String fileName2 = saveFile(file2, uploadDir);
        String fileName3 = saveFile(file3, uploadDir);

        // 3. 엔티티 저장 (DB에는 파일명만 저장!)
        Post newPost = Post.builder()
                .userId(user.getUserId())
                .postTitle(postTitle)
                .postFile1(fileName1)
                .postFile2(fileName2)
                .postFile3(fileName3)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // ✅ 반드시 명시적으로 Content 설정
        newPost.setContent(content);

        Post savedPost = postRepository.save(newPost);
        return savedPost.getPostSeq();
    }

    // ✅ 저장은 uploads/ 폴더에, DB에는 파일명만 저장
    private String saveFile(MultipartFile file, String uploadDir) throws IOException {
        if (file == null || file.isEmpty()) return null;

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created && !dir.exists()) {
                throw new IOException("업로드 디렉토리 생성 실패: " + uploadDir);
            }
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String filePath = uploadDir + fileName;

        file.transferTo(new File(filePath));

        // DB에는 파일명만 저장
        return fileName;
    }

    public void updateReviewBySeq(Long postSeq, String content, int rating) {
        Optional<Post> optionalPost = postRepository.findById(postSeq);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setPostContent(content);
            post.setPostRating(rating);
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.save(post);
        } else {
            throw new RuntimeException("postSeq " + postSeq + "에 해당하는 게시글이 없습니다.");
        }
    }

    public Optional<Post> findPostBySeq(Long postSeq) {
        return postRepository.findById(postSeq);
    }

    @Transactional
    public int likePost(Long postSeq, String username) {
        System.out.println("likePost 실행: postSeq = " + postSeq + ", username = " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저 없음: " + username));

        Post post = postRepository.findByPostSeq(postSeq)
                .orElseThrow(() -> new RuntimeException("리뷰 없음: postSeq = " + postSeq));

        // ⭐ null 방지 체크
        if (post.getUserId() == null || user.getUserId() == null) {
            throw new RuntimeException("userId가 null입니다");
        }

        if (post.getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("본인 리뷰에는 좋아요를 누를 수 없습니다");
        }

        Optional<PostLike> existingLike = postLikeRepository.findByPostSeqAndUserId(postSeq, user.getUserId());

        if (existingLike.isPresent()) {
            postLikeRepository.delete(existingLike.get());
            System.out.println("좋아요 취소");
        } else {
            postLikeRepository.save(new PostLike(postSeq, user.getUserId()));
            System.out.println("❤️ 좋아요 등록");
        }

        int count = postLikeRepository.countByPostSeq(postSeq);
        System.out.println("현재 좋아요 수: " + count);
        return count;
    }
    public List<PostResponse> getPostResponsesByContentId(Long contentId) {
        List<Post> posts = postRepository.findByContent_IdOrderByCreatedAtDesc(contentId);

        return posts.stream()
                .map(post -> {
                    // 유저 닉네임 조회
                    String nickname = userRepository.findById(post.getUserId())
                            .map(User::getNickname)
                            .orElse("알수없음");

                    // 좋아요 수 계산
                    int likeCount = postLikeRepository.countByPostSeq(post.getPostSeq());

                    return PostResponse.fromWithNickname(post, nickname, likeCount);
                })
                .collect(Collectors.toList());
    }





}
