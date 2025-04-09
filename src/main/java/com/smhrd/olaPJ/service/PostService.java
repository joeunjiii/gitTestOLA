package com.smhrd.olaPJ.service;

import com.smhrd.olaPJ.domain.Post;
import com.smhrd.olaPJ.dto.ContentRequest;
import com.smhrd.olaPJ.dto.PostResponse;
import com.smhrd.olaPJ.repository.ContentRepository;
import com.smhrd.olaPJ.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService{

    private final PostRepository postRepository;

    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }
}
