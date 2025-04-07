package com.smhrd.olaPJ.service;

import com.smhrd.olaPJ.domain.Genre;
import com.smhrd.olaPJ.dto.GenreRequest;
import com.smhrd.olaPJ.repository.GenreRepository;
import com.smhrd.olaPJ.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final UserRepository userRepository;

    public void saveGenre(GenreRequest request, String userName) {
        // USER_NAME으로 USER_ID(UUID) 가져오기
        String userId = userRepository.findByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보 없음"))
                .getUserId();  // UUID 가져오기

        Genre genre = Genre.builder()
                .userId(userId)  // 여기에 UUID 설정됨
                .romance("Y".equals(request.getRomance()) ? 'Y' : 'N')
                .comedy("Y".equals(request.getComedy()) ? 'Y' : 'N')
                .thriller("Y".equals(request.getThriller()) ? 'Y' : 'N')
                .animation("Y".equals(request.getAnimation()) ? 'Y' : 'N')
                .action("Y".equals(request.getAction()) ? 'Y' : 'N')
                .drama("y".equals(request.getDrama()) ? 'Y' : 'N')
                .horror("Y".equals(request.getHorror()) ? 'Y' : 'N')
                .fantasy("Y".equals(request.getFantasy()) ? 'Y' : 'N')
                .ottPlatform(request.getOttPlatform())
                .director(request.getDirector())
                .characters(request.getCharacters())
                .latestYear(request.isLatestYear())
                .build();

        genreRepository.save(genre);
    }
}

