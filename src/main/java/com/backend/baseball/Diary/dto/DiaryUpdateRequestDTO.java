package com.backend.baseball.Diary.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class DiaryUpdateRequestDTO {
    private List<Long> uploadImages;  // 이미지 ID 리스트
    private String content;
}
