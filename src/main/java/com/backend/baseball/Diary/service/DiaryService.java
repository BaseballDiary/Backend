package com.backend.baseball.Diary.service;

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

@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final GameInfoRepository gameInfoRepository;
    private final HttpSession httpSession;

    /**
     * ✅ 1. 날짜와 유저 클럽을 기반으로 경기 일정 가져오기
     */
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

    /**
     * 2. 경기 일정 선택 후 야구 일기 작성 및 저장
     */
    @Transactional
    public Diary save(AddDiaryRequest request, HttpSession session) {
        // 1. 세션에서 로그인된 사용자 가져오기
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        // 2. 사용자 팀 정보 + 날짜로 경기 찾기
        GameInfo gameInfo = gameInfoRepository.findById(request.getGameId())
                .orElseThrow(() -> new IllegalStateException("해당 경기 정보를 찾을 수 없습니다."));

        // 3. 일기 저장
        Diary diary = request.toEntity(user, gameInfo);
        return diaryRepository.save(diary);
    }

    @Transactional
    public Diary updateDiary(Long diaryId, UpdateDiaryRequest request, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalStateException("해당 일기를 찾을 수 없습니다."));

        // ✅ gameId 변경 처리 추가
        GameInfo newGameInfo = gameInfoRepository.findById(request.getGameId())
                .orElseThrow(() -> new IllegalStateException("해당 경기 정보를 찾을 수 없습니다."));

        diary.update(request.getDate(), request.getViewType(), request.getContent(), request.getImgUrl(), newGameInfo);

        return diaryRepository.save(diary);



    }

    @Transactional
    public void deleteDiary(Long diaryId, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalStateException("해당 일기를 찾을 수 없습니다."));

        // 작성자 확인: 본인의 일기만 삭제 가능
        if (!diary.getUser().equals(user)) {
            throw new IllegalStateException("본인의 일기만 삭제할 수 있습니다.");
        }

        diaryRepository.delete(diary);
    }


}
