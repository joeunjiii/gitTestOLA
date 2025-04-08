package com.smhrd.olaPJ.service;

import com.smhrd.olaPJ.domain.Genre;
import com.smhrd.olaPJ.domain.User;
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
        //User 객체 가져오기
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보 없음"));

        String userId = user.getUserId();  // UUID

        //이미 저장된 장르가 있는지 확인 → 있으면 저장하지 않음
        if (genreRepository.existsByUserId(userId)) {
            System.out.println("이미 장르 정보가 존재함. 저장 생략");
            return;
        }

        // 3. Genre 객체 생성
        Genre genre = Genre.builder()
                .userId(userId)
                .romance("Y".equals(request.getRomance()) ? 'Y' : 'N')
                .comedy("Y".equals(request.getComedy()) ? 'Y' : 'N')
                .thriller("Y".equals(request.getThriller()) ? 'Y' : 'N')
                .animation("Y".equals(request.getAnimation()) ? 'Y' : 'N')
                .action("Y".equals(request.getAction()) ? 'Y' : 'N')
                .drama("Y".equalsIgnoreCase(request.getDrama()) ? 'Y' : 'N')
                .horror("Y".equals(request.getHorror()) ? 'Y' : 'N')
                .fantasy("Y".equals(request.getFantasy()) ? 'Y' : 'N')
                .ottPlatform(request.getOttPlatform())
                .director(request.getDirector())
                .characters(request.getCharacters())
                .latestYear(request.isLatestYear())
                .build();

        // 장르 DB 저장
        genreRepository.save(genre);

        // 장르선택 페이지에서 체크 후 다음 버튼 -> genreSelected == 0 -> 1로 바뀜(저장되었다는 뜻)
        user.setGenreSelected(1);
        userRepository.save(user);


    }

    }

