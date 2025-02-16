package com.backend.baseball.Diary.dto.CreateDiary;

import com.backend.baseball.GameInfo.entity.GameInfo;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
        return GameInfoResponseDTO.builder()
                .gameId(gameInfo.getGameCertificateId())
                .team1(gameInfo.getTeam1())
                .team2(gameInfo.getTeam2())
                .team1Score(Integer.parseInt(gameInfo.getTeam1Score()))
                .team2Score(Integer.parseInt(gameInfo.getTeam2Score()))
                .gameDate(gameInfo.getGameDate())
                .day("")
                .time(gameInfo.getTime())
                .location(gameInfo.getPlace())
                .gameStatus(gameInfo.getGameStatus())
                .build();
    }
}