package com.backend.baseball.Diary.dto.CreateDiary;

import com.backend.baseball.Diary.enums.ViewType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryAddRequestDTO {
    private Long diaryId;
    private Long gameId;
    private String contents;
    private List<String> imgUrls;
    private ViewType viewType;  // 추가된 필드
    private int score;
}
