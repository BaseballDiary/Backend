package com.backend.baseball.Home.dto;

import com.backend.baseball.GameInfo.entity.GameInfo;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MyInfoResponseDTO {
    private Long gameId;
    private LocalDate gameDate;
    private String homeTeam;
    private String awayTeam;
    private String gameTime;
    private String stadium;
    private String status;

    public static MyInfoResponseDTO fromEntity(GameInfo gameInfo) {
        return MyInfoResponseDTO.builder()
                .gameId(gameInfo.getGameCertificateId())
                .gameDate(gameInfo.getGameDate())
                .homeTeam(gameInfo.getTeam1())
                .awayTeam(gameInfo.getTeam2())
                .gameTime(gameInfo.getTime())
                .stadium(gameInfo.getPlace())
                .status(gameInfo.getGameStatus())
                .build();
    }
}
