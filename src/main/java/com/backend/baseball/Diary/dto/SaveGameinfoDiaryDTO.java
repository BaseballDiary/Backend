package com.backend.baseball.Diary.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveGameinfoDiaryDTO {

    @NotNull
    private Long gameId;  // 경기 ID

    private String contents;  // 다이어리 내용 (초기에는 빈 값)

    private String imgUrl;  // 이미지 URL (초기에는 빈 값)
}
