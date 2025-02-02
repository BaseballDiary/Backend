package com.backend.baseball.Diary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class DiaryResponse {
    private int year;
    private Stats myStats;
    private Stats teamStats;
    private List<DiaryEntry> onSiteDiarys;
    private List<DiaryEntry> atHomeDiarys;

    @Getter
    @AllArgsConstructor
    public static class Stats {
        private int myWins;
        private int myLosses;
        private int myDraws;
        private int myGames;
        private int myWinRate;
    }

    @Getter
    @AllArgsConstructor
    public static class DiaryEntry {
        private Long id;
        private String stadium;
        private Score score;
        private boolean result;
        private String time;
        private String date;
        private String dayOfWeek;

        //getDate() 추가 (LocalDate로 변환)
        public LocalDate getLocalDate() {
            return LocalDate.parse(this.date);
        }
        @Getter
        @AllArgsConstructor
        public static class Score {
            private String myTeam;
            private int myScore;
            private String opponentTeam;
            private int opponentScore;
        }
    }
}
