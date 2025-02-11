package com.backend.baseball.Diary.service;

import com.backend.baseball.Diary.dto.DiaryResponseDTO;
import com.backend.baseball.Diary.dto.SaveDiaryRequestDTO;
import com.backend.baseball.Diary.entity.Diary;
import com.backend.baseball.Diary.repository.DiaryRepository;
import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.GameInfo.repository.GameInfoRepository;
import com.backend.baseball.Login.entity.User;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final GameInfoRepository gameInfoRepository;
    private final HttpSession httpSession;

    //1. 날짜와 유저 클럽을 기반으로 경기 일정 가져오기

    @Transactional
    public GameInfo getGameInfoByDate(String date, HttpSession session) {
        // 1. 세션에서 로그인된 사용자 가져오기
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        // 2. 날짜 변환
        LocalDate gameDate = LocalDate.parse(date);

        // 3. 유저의 클럽 정보 가져오기
        String club = user.getMyClub();

        // 4. 해당 날짜와 클럽에 맞는 경기 찾기
        return gameInfoRepository.findGamesByClubAndDate(club, gameDate)
                .orElseThrow(() -> new IllegalStateException("해당 날짜의 경기 정보를 찾을 수 없습니다."));
    }

    // 📌 다이어리 저장
    @Transactional
    public DiaryResponseDTO saveGameToDiary(SaveDiaryRequestDTO request, HttpSession session) {
        // 1. 세션에서 로그인된 사용자 가져오기
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        // 2. gameId를 이용하여 해당 경기 정보 가져오기
        GameInfo gameInfo = gameInfoRepository.findById(request.getGameId())
                .orElseThrow(() -> new IllegalStateException("해당 경기를 찾을 수 없습니다."));

        // 3. Diary 객체 생성
        Diary diary = Diary.builder()
                .user(user)
                .gameInfo(gameInfo)
                .content(request.getContents() != null ? request.getContents() : "") // 기본값 빈 문자열
                .imgUrl(request.getImgUrl() != null ? Collections.singletonList(request.getImgUrl()) : Collections.emptyList()) // ✅ List<String> 변환
                .build();

        // 4. Diary 저장
        diary = diaryRepository.save(diary);

        // 5. 저장된 Diary를 ResponseDTO로 변환하여 반환
        return DiaryResponseDTO.fromEntity(diary);
    }

}
