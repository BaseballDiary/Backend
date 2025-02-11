package com.backend.baseball.Diary.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveDiaryRequestDTO {
    private Long gameId;    // 다이어리를 작성할 경기 ID
    private String contents;  // 다이어리 내용
    private String imgUrl;    // 이미지 URL (선택 사항)

    // 기본 생성자 필요 (Lombok 사용)
    public SaveDiaryRequestDTO() {}
}
