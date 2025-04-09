package com.smhrd.olaPJ.service;

import com.smhrd.olaPJ.dto.ContentRequest;
import com.smhrd.olaPJ.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.smhrd.olaPJ.domain.Content;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    public List<ContentRequest> searchByTitle(String keyword) {
        List<Content> contentList = contentRepository.findByTitleContainingIgnoreCase(keyword);

        return contentList.stream()
                .map(content -> new ContentRequest(content.getTitle(), content.getPosterImg()))
                .collect(Collectors.toList());
    }
}
