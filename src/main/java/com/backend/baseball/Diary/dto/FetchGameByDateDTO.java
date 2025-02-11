package com.backend.baseball.Diary.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

//야구 일기 작성 처음에
@Getter
@Setter
public class FetchGameByDateDTO {
    @NotNull
    private LocalDate gameDate; //"2024-05-02"


}
