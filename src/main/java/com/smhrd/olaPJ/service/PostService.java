package com.smhrd.olaPJ.service;

import com.smhrd.olaPJ.domain.Post;
import com.smhrd.olaPJ.domain.PostLike;
import com.smhrd.olaPJ.domain.User;
import com.smhrd.olaPJ.dto.PostResponse;
//import com.smhrd.olaPJ.repository.PostLikeRepository;
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
//    private final PostLikeRepository postLikeRepository;


    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }

    public Long uploadReview(String postTitle, MultipartFile file1, MultipartFile file2, MultipartFile file3, String username) throws IOException {
        // 1. 실제 유저 찾기
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("해당 유저가 존재하지 않습니다: " + username);
        }
        String userId = optionalUser.get().getUserId(); // UUID

        // 2. 파일 저장 (루트/uploads 폴더 사용)
        String uploadDir = new File("src/main/resources/static/uploads/").getAbsolutePath() + "/";
        String fileName1 = saveFile(file1, uploadDir);
        String fileName2 = saveFile(file2, uploadDir);
        String fileName3 = saveFile(file3, uploadDir);

        // 3. 엔티티 저장 (DB에는 파일명만 저장!)
        Post newPost = Post.builder()
                .userId(userId)
                .postTitle(postTitle)
                .postFile1(fileName1)
                .postFile2(fileName2)
                .postFile3(fileName3)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

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

//    @Transactional
//    public int likePost(Long postSeq, String username) {
//
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("유저 없음"));
//        String userId = user.getUserId();
//
//        Post post = postRepository.findByPostSeq(postSeq);
//        if (post == null) {
//            throw new RuntimeException("리뷰를 찾을 수 없습니다");
//        }
//
//        Optional<PostLike> existingLike = postLikeRepository.findBPostSeqAndUserId(postSeq, userId);
//
//        if (existingLike.isPresent()) {
//            PostLike postLike = existingLike.get();
//        } else {
//            PostLike postLike = new PostLike(postSeq, userId);
//            postLikeRepository.save(postLike);
//        }
//
//        return postLikeRepository.countByPostSeq(postSeq);
//
//
//
//
//    }
}