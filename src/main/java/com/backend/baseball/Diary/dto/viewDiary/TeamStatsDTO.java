package com.backend.baseball.Diary.dto.viewDiary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TeamStatsDTO {
    private int teamWins;
    private int teamLosses;
    private int teamDraws;
    private int teamGames;
    private int teamWinRate;
}
