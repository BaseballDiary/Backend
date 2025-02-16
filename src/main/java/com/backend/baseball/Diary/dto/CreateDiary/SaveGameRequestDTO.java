package com.backend.baseball.Diary.dto.CreateDiary;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class SaveGameRequestDTO {
    private Long gameId;
    private String day; // 요일
}
