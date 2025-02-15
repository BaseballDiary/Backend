package com.backend.baseball.Diary.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaveDiaryRequestDTO {
    private Long gameId;         // 다이어리를 작성할 경기 ID
    private String contents;     // 다이어리 내용
    private List<String> imgUrls;  // 이미지 URL 리스트 (여러 개 저장 가능)

    // 기본 생성자 필요 (Lombok 사용)
    public SaveDiaryRequestDTO() {}
}
