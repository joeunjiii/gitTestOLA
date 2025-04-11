package com.smhrd.olaPJ.service;

import com.smhrd.olaPJ.domain.Genre;
import com.smhrd.olaPJ.domain.User;
import com.smhrd.olaPJ.dto.GenreRequest;
import com.smhrd.olaPJ.repository.GenreRepository;
import com.smhrd.olaPJ.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
                .ottPlatform(request.getOttPlatform().toString())
                .latestYear(request.isLatestYear())
                .build();

        // 장르 DB 저장
        genreRepository.save(genre);

        // 장르선택 페이지에서 체크 후 다음 버튼 -> genreSelected == 0 -> 1로 바뀜(저장되었다는 뜻)
        user.setGenreSelected(1);
        userRepository.save(user);


    }

    public void saveOttPlatform(GenreRequest request, String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보 없음"));

        Genre genre = genreRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("장르 정보 없음"));

        // 선택된 OTT 플랫폼 리스트를 하나의 문자열로 저장
        String platformString = String.join(",", request.getOttPlatform());
        genre.setOttPlatform(platformString);
        genre.setLatestYear(request.isLatestYear());

        // 꼭 저장해줘야 DB에 반영됨!
        genreRepository.save(genre);
    }

    @Transactional
    public void saveSelectedTitle(String username, String selectedTitle)
    {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보 없음"));
        String userId = user.getUserId();

        Genre genre = genreRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("장르 정보 없음"));

        genre.setSelectedTitle(selectedTitle);
        genreRepository.save(genre);
    }

    @Transactional
    public void updateGenres(String userId, Map<String, String> genresMap) {
        Genre genre = genreRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("장르 정보 없음"));

        // 모든 장르에 대해 Y/N 세팅
        genre.setRomance(getYnValue(genresMap.get("romance")));
        genre.setComedy(getYnValue(genresMap.get("comedy")));
        genre.setThriller(getYnValue(genresMap.get("thriller")));
        genre.setAnimation(getYnValue(genresMap.get("animation")));
        genre.setAction(getYnValue(genresMap.get("action")));
        genre.setDrama(getYnValue(genresMap.get("drama")));
        genre.setHorror(getYnValue(genresMap.get("horror")));
        genre.setFantasy(getYnValue(genresMap.get("fantasy")));

        genreRepository.save(genre);
    }

    // 유틸 메서드: null이면 'N', 'Y'/'N'이면 그대로 char로 변환
    private char getYnValue(String value) {
        return (value != null && value.equalsIgnoreCase("Y")) ? 'Y' : 'N';
    }



    public List<String> getGenresByUserId(String userId) {
        return genreRepository.findGenreNamesByUserId(userId);
    }
}