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
                .getUserId();  // UUID

        Genre genre = Genre.builder()
                .userId(userId)  // 여기에 UUID 설정됨
                .romance(request.getRomance() != null ? 'Y' : 'N')
                .codeDy(request.getComedy() != null ? 'Y' : 'N')
                .thriller(request.getThriller() != null ? 'Y' : 'N')
                .animation(request.getAnimation() != null ? 'Y' : 'N')
                .action(request.getAction() != null ? 'Y' : 'N')
                .drama(request.getDrama() != null ? 'Y' : 'N')
                .horror(request.getHorror() != null ? 'Y' : 'N')
                .fantasy(request.getFantasy() != null ? 'Y' : 'N')
                .ottPlatform(request.getOttPlatform())
                .director(request.getDirector())
                .characters(request.getCharacters())
                .latestYear(request.isLatestYear())
                .build();

        genreRepository.save(genre);
    }
}

