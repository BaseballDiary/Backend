package com.backend.baseball.Diary.dto.CreateDiary;

import com.backend.baseball.GameInfo.entity.GameInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class GameInfoResponseDTO {
    private Long gameId;
    private String team1;
    private String team2;
    private Integer team1Score;
    private Integer team2Score;
    private LocalDate gameDate;
    private String day;
    private String time;
    private String location;
    private String gameStatus;

    // 엔티티 -> DTO 변환 메서드
    public static GameInfoResponseDTO fromEntity(GameInfo gameInfo) {
        return new GameInfoResponseDTO(
                gameInfo.getGameCertificateId(),
                gameInfo.getTeam1(),
                gameInfo.getTeam2(),
                gameInfo.getTeam1Score() != null ? Integer.parseInt(gameInfo.getTeam1Score()) : null,
                gameInfo.getTeam2Score() != null ? Integer.parseInt(gameInfo.getTeam2Score()) : null,
                gameInfo.getGameDate(),
                gameInfo.getGameDate().getDayOfWeek().toString(), // 요일 변환
                gameInfo.getTime(),
                gameInfo.getPlace(),
                gameInfo.getGameStatus()
        );
    }
}