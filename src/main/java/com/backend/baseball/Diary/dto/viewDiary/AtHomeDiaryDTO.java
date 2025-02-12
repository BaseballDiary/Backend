package com.backend.baseball.Diary.dto.viewDiary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AtHomeDiaryDTO {
    private Long id;
    private String myTeam;
    private int myScore;
    private String opponentTeam;
    private int opponentScore;
    private boolean result;
    private String date;
    private String dayOfWeek;
}
