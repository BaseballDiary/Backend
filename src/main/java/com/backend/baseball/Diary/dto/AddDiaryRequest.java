package com.backend.baseball.Diary.dto;

import com.backend.baseball.Diary.entity.Diary;
import com.backend.baseball.Diary.enums.ViewType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter

public class AddDiaryRequest {
    private ViewType viewType;
    private String contents;
    private String imgUrl;

    public Diary toEntity(){
        return Diary.builder()
                .viewType(viewType)
                .contents(contents)
                .imgUrl(imgUrl)
                .build();
    }
}
