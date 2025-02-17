package com.backend.baseball.Diary.dto.CreateDiary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryAddRequestDTO {
    private Long certificateId;
    private Long gameId;
    private String contents;
    private List<String> imgUrls;
}
