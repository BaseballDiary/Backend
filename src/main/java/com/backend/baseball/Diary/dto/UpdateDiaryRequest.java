package com.backend.baseball.Diary.dto;

import com.backend.baseball.Diary.enums.ViewType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
public class UpdateDiaryRequest {
    private Long gameId;
    private LocalDate date;
    private ViewType viewType;
    private String content;
    private List<String> imgUrl;

    public UpdateDiaryRequest(Long gameId, LocalDate date, ViewType viewType, String content, List<String> imgUrl) {
        this.gameId = gameId;
        this.date = date;
        this.viewType = viewType;
        this.content = content;
        this.imgUrl = imgUrl;
    }
}
