package com.backend.baseball.Diary.controller;

import com.backend.baseball.Diary.entity.Diary;
import com.backend.baseball.Diary.enums.ViewType;
import com.backend.baseball.Diary.repository.DiaryRepository;
import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.GameInfo.repository.GameInfoRepository;
import com.backend.baseball.GameInfo.repository.TeamRankingRepository;
import com.backend.baseball.User.entity.User;
import com.backend.baseball.User.helper.AccountHelper;
import com.backend.baseball.User.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.baseball.GameInfo.entity.TeamRanking; // TeamRanking 엔티티 import
import com.backend.baseball.Diary.dto.Stat.TeamStatResponseDTO; // DTO import


import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/diary")
public class StatController {


    private final TeamRankingRepository teamRankingRepository;
    private final UserRepository userRepository;
    private final AccountHelper accountHelper;
    private final DiaryRepository diaryRepository;
    private final GameInfoRepository gameInfoRepository;

    @GetMapping("/teamstat/{year}")
    public ResponseEntity<?> getTeamStatsByYear(
        @PathVariable("year") String year,
        HttpServletRequest req) {

        //현재 로그인한 사용자의 팀 정보 가져오기
        Long memberId = accountHelper.getMemberId(req);
        if (memberId == null) {
            return ResponseEntity.status(401).body("{\"status\": 401, \"message\": \"로그인이 필요합니다.\"}");
        }

        Optional<User> userOptional = userRepository.findById(memberId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body("{\"status\": 404, \"message\": \"사용자를 찾을 수 없습니다.\"}");
        }

        String myTeam = userOptional.get().getMyClub(); // 내 구단 이름 가져오기

        //해당 년도의 내 팀 랭킹 정보 조회
        Optional<TeamRanking> teamRankingOpt = teamRankingRepository.findByYearAndTeamName(year, myTeam);

        if (teamRankingOpt.isEmpty()) {
            return ResponseEntity.status(404).body("{\"status\": 404, \"message\": \"해당 년도의 팀 정보를 찾을 수 없습니다.\"}");
        }

        // DTO 변환 후 응답
        TeamStatResponseDTO responseDTO = TeamStatResponseDTO.fromEntity(teamRankingOpt.get());
        return ResponseEntity.ok(responseDTO);
    }

    // 특정 년도의 직관 경기 통계 가져오기
    @GetMapping("/mystat/{year}")
    public ResponseEntity<?> getMyStatsByYear(
            @PathVariable("year") String year,
            HttpServletRequest req) {

        // 📌 year를 int로 변환
        int yearInt;
        try {
            yearInt = Integer.parseInt(year);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("{\"status\": 400, \"message\": \"유효한 연도를 입력하세요.\"}");
        }

        // 📌 현재 로그인한 사용자 정보 가져오기
        Long memberId = accountHelper.getMemberId(req);
        if (memberId == null) {
            return ResponseEntity.status(401).body("{\"status\": 401, \"message\": \"로그인이 필요합니다.\"}");
        }

        Optional<User> userOptional = userRepository.findById(memberId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body("{\"status\": 404, \"message\": \"사용자를 찾을 수 없습니다.\"}");
        }

        User user = userOptional.get();
        String myClub = user.getMyClub(); // 사용자의 팀 정보 가져오기

        // 📌 해당 연도의 사용자가 작성한 `onSite` 일기 가져오기
        List<Diary> onSiteGames = diaryRepository.findByUserAndViewTypeAndDateBetween(
                user, ViewType.onSite,
                LocalDate.of(yearInt, 1, 1),
                LocalDate.of(yearInt, 12, 31)
        );

        // 📌 경기 결과 분석을 위한 변수
        int wins = 0, losses = 0, draws = 0;
        int totalGames = onSiteGames.size(); // 일기 개수가 실제 직관한 경기 수

        for (Diary diary : onSiteGames) {
            GameInfo gameInfo = diary.getGameInfo();
            if (gameInfo != null && gameInfo.getTeam1Score() != null && gameInfo.getTeam2Score() != null) {
                int team1Score = Integer.parseInt(gameInfo.getTeam1Score());
                int team2Score = Integer.parseInt(gameInfo.getTeam2Score());

                // 📌 내 구단이 팀1인지 팀2인지 판별
                boolean isTeam1 = gameInfo.getTeam1().equals(myClub);
                boolean isTeam2 = gameInfo.getTeam2().equals(myClub);

                if (isTeam1 || isTeam2) {
                    if ((isTeam1 && team1Score > team2Score) || (isTeam2 && team2Score > team1Score)) {
                        wins++;
                    } else if ((isTeam1 && team1Score < team2Score) || (isTeam2 && team2Score < team1Score)) {
                        losses++;
                    } else {
                        draws++;
                    }
                }
            }
        }

        // 📌 승률 계산 (총 경기 수가 0이면 0%)
        int winRate = totalGames > 0 ? (int) Math.round((double) wins / totalGames * 100) : 0;

        // 📌 최종 응답 JSON 반환
        return ResponseEntity.ok(String.format(
                "{\"myWins\": %d, \"myLosses\": %d, \"myDraws\": %d, \"myGames\": %d, \"myWinRate\": %d}",
                wins, losses, draws, totalGames, winRate
        ));
    }

}
