package com.backend.baseball.Diary.dto;

import com.backend.baseball.Diary.entity.Diary;
import com.backend.baseball.Diary.enums.ViewType;
import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.Login.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter

public class AddDiaryRequest {
    private Long gameId;
    private LocalDate date;
    private ViewType viewType;
    private String contents;
    private List<String> imgUrl; // 리스트 형태로 변경

    public Diary toEntity(User user, GameInfo gameInfo){
        return Diary.builder()
                .date(date)
                .viewType(viewType)
                .content(contents)
                .imgUrl(imgUrl)
                .user(user)
                .gameInfo(gameInfo)
                .build();
    }
}
