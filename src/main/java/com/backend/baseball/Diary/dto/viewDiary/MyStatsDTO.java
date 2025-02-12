package com.backend.baseball.Diary.dto.viewDiary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MyStatsDTO {
    private long myWins;
    private long myLosses;
    private long myDraws;
    private long myGames;
    private int myWinRate;
}
