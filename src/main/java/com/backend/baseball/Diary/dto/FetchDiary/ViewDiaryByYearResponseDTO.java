package com.backend.baseball.Diary.dto.FetchDiary;
import com.backend.baseball.Diary.entity.Diary;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ViewDiaryByYearResponseDTO {
    private String date;
    private String dayOfWeek;
    private String time;
    private String stadium;
    private String result;  // ✅ result 필드를 String 타입으로 변경
    private String myTeam;
    private int myScore;
    private String opponentTeam;
    private int opponentScore;
    private List<String> uploadImages;
    private String content;

    public static ViewDiaryByYearResponseDTO fromEntity(Diary diary) {
        return ViewDiaryByYearResponseDTO.builder()
                .date(diary.getDate().toString())
                .dayOfWeek(diary.getDay()) // 요일 그대로 사용
                .time(diary.getGameInfo().getTime())
                .stadium(diary.getGameInfo().getPlace())
                .result(diary.getGameInfo().getGameStatus()) // ✅ 승리, 패배, 무승부 그대로 반환
                .myTeam(diary.getGameInfo().getTeam1())
                .myScore(Integer.parseInt(diary.getGameInfo().getTeam1Score()))  // 문자열을 정수로 변환
                .opponentTeam(diary.getGameInfo().getTeam2())
                .opponentScore(Integer.parseInt(diary.getGameInfo().getTeam2Score()))
                .uploadImages(diary.getImgUrls()) // 이미지 리스트 그대로 사용
                .content(diary.getContents())
                .build();
    }
}
