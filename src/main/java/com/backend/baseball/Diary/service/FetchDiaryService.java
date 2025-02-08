package com.backend.baseball.Diary.service;

import com.backend.baseball.Diary.entity.Diary;
import com.backend.baseball.Diary.enums.ViewType;
import com.backend.baseball.Diary.repository.DiaryRepository;
import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.Login.entity.User;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class FetchDiaryService {

    private final DiaryRepository diaryRepository;

    //유저의 점수 가져오기
    private int getMyScore(GameInfo gameInfo, String myTeam) {
        if (gameInfo.getTeam1().equals(myTeam)) {
            return Integer.parseInt(gameInfo.getTeam1Score());
        } else if (gameInfo.getTeam2().equals(myTeam)) {
            return Integer.parseInt(gameInfo.getTeam2Score());
        } else {
            throw new IllegalStateException("유저의 팀이 이 경기와 일치하지 않습니다.");
        }
    }

    //상대팀 점수 가져오기
    private int getOpponentScore(GameInfo gameInfo, String myTeam) {
        if (gameInfo.getTeam1().equals(myTeam)) {
            return Integer.parseInt(gameInfo.getTeam2Score());
        } else if (gameInfo.getTeam2().equals(myTeam)) {
            return Integer.parseInt(gameInfo.getTeam1Score());
        } else {
            throw new IllegalStateException("유저의 팀이 이 경기와 일치하지 않습니다.");
        }
    }

    @Transactional
    public DiaryResponse getDiariesByYear(int year, HttpSession session) {
        // 1️⃣ 로그인된 사용자 가져오기
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        List<Diary> diaries = diaryRepository.findByUserAndYear(user, year);

        // 승패 기록 초기화
        int myWins = 0, myLosses = 0, myDraws = 0;
        int teamWins = 87, teamLosses = 55, teamDraws = 2, teamGames = 144; // 예제 데이터

        List<DiaryResponse.DiaryEntry> onSiteDiaries = new ArrayList<>();
        List<DiaryResponse.DiaryEntry> atHomeDiaries = new ArrayList<>();

        for (Diary diary : diaries) {
            GameInfo gameInfo = diary.getGameInfo();
            String myTeam = user.getMyClub();

            //getMyScore, getOpponentScore 사용
            int myScore = getMyScore(gameInfo, myTeam);
            int opponentScore = getOpponentScore(gameInfo, myTeam);

            boolean isWin = myScore > opponentScore;
            boolean isDraw = myScore == opponentScore;

            if (isWin) myWins++;
            else if (isDraw) myDraws++;
            else myLosses++;

            LocalDate date = gameInfo.getGameDate();
            String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);

            DiaryResponse.DiaryEntry entry = new DiaryResponse.DiaryEntry(
                    diary.getDiaryId(),
                    gameInfo.getPlace(),
                    new DiaryResponse.DiaryEntry.Score(
                            myTeam,
                            myScore,
                            gameInfo.getTeam1().equals(myTeam) ? gameInfo.getTeam2() : gameInfo.getTeam1(),
                            opponentScore
                    ),
                    isWin,
                    gameInfo.getTime(),
                    date.toString(),
                    dayOfWeek
            );

            if (diary.getViewType() == ViewType.onSite) {
                onSiteDiaries.add(entry);
            } else {
                atHomeDiaries.add(entry);
            }
        }

        int myGames = myWins + myLosses + myDraws;
        int myWinRate = (myGames > 0) ? (myWins * 100) / myGames : 0;
        int teamWinRate = (teamGames > 0) ? (teamWins * 100) / teamGames : 0;

        return new DiaryResponse(
                year,
                new DiaryResponse.Stats(myWins, myLosses, myDraws, myGames, myWinRate),
                new DiaryResponse.Stats(teamWins, teamLosses, teamDraws, teamGames, teamWinRate),
                onSiteDiaries.stream()
                        .sorted(Comparator.comparing(DiaryResponse.DiaryEntry::getLocalDate).reversed())
                        .limit(10)
                        .toList(),
                atHomeDiaries.stream()
                        .sorted(Comparator.comparing(DiaryResponse.DiaryEntry::getLocalDate).reversed())
                        .limit(10)
                        .toList()
        );
    }
}