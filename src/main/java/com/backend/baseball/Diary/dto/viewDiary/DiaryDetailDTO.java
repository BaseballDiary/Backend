package com.backend.baseball.Diary.dto.viewDiary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DiaryDetailDTO {
    private String date;
    private String dayOfWeek;
    private String time;
    private String stadium;
    private boolean result;
    private String myTeam;
    private int myScore;
    private String opponentTeam;
    private int opponentScore;
    private List<String> uploadImages;
    private String content;
}
