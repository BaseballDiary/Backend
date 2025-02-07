package com.backend.baseball.Diary.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

//날짜로 경기 정보 가져올 때
@Getter
@Builder
public class GameResponseDTO {
    private Long gameId;
    private String team1;
    private String team2;
    private int team1Score;
    private int team2Score;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate gameDate;

    private String day; //요일

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private String time;

    private String location;
    private String gameStatus;

    public static GameResponseDTO of(Long gameId, String team1, String team2, int team1Score, int team2Score,
                                     LocalDate gameDate, String day, String time, String location, String gameStatus){
        return GameResponseDTO.builder()
                .gameId(gameId)
                .team1(team1)
                .team2(team2)
                .team1Score(team1Score)
                .team2Score(team2Score)
                .gameDate(gameDate)
                .day(day)
                .time(time)
                .location(location)
                .gameStatus(gameStatus)
                .build();
    }
}
