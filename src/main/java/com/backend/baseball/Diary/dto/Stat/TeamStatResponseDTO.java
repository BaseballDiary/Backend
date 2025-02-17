package com.backend.baseball.Diary.dto.Stat;

import com.backend.baseball.GameInfo.entity.TeamRanking;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class TeamStatResponseDTO {
    private int teamWins;      // 승
    private int teamLosses;    // 패
    private int teamDraws;     // 무
    private int teamGames;     // 경기 수
    private int teamWinRate;   // 승률 (퍼센트 변환)

    public static TeamStatResponseDTO fromEntity(TeamRanking teamRanking) {
        return TeamStatResponseDTO.builder()
                .teamWins(Integer.parseInt(teamRanking.getWin()))
                .teamLosses(Integer.parseInt(teamRanking.getLose()))
                .teamDraws(Integer.parseInt(teamRanking.getTie()))
                .teamGames(Integer.parseInt(teamRanking.getGameNum()))
                .teamWinRate((int) (Double.parseDouble(teamRanking.getWinningRate()) * 100)) // 소수점 → 퍼센트 변환
                .build();
    }
}
