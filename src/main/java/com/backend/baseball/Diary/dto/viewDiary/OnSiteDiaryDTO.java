package com.backend.baseball.Diary.dto.viewDiary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OnSiteDiaryDTO {
    private Long id;
    private String stadium;
    private String myTeam;
    private int myScore;
    private String opponentTeam;
    private int opponentScore;
    private boolean result;
    private String time;
    private String date;
    private String dayOfWeek;
}
