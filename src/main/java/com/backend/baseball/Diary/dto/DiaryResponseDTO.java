package com.backend.baseball.Diary.dto;

import com.backend.baseball.Diary.entity.Diary;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class DiaryResponseDTO {

    private Long diaryId;
    private Long userId;
    private Long gameId;

    private String team1;
    private String team2;
    private int team1Score;
    private int team2Score;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate gameDate;

    private String day; // 요일

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private String time;

    private String location;
    private String gameStatus;

    private String contents;  // 다이어리 내용
    private List<String> imgUrl;  // 이미지 경로

    // 📌 Diary 엔티티를 ResponseDTO로 변환
    public static DiaryResponseDTO fromEntity(Diary diary) {
        return DiaryResponseDTO.builder()
                .diaryId(diary.getDiaryId())
                .userId(diary.getUser().getCertificateId())
                .gameId(diary.getGameInfo().getGameCertificateId())
                .team1(diary.getGameInfo().getTeam1())
                .team2(diary.getGameInfo().getTeam2())
                .team1Score(parseScore(diary.getGameInfo().getTeam1Score())) // ✅ String → int 변환
                .team2Score(parseScore(diary.getGameInfo().getTeam2Score()))
                .gameDate(diary.getGameInfo().getGameDate())
                .day(diary.getGameInfo().getGameDate().getDayOfWeek().toString())
                .time(diary.getGameInfo().getTime())
                .location(diary.getGameInfo().getPlace())
                .gameStatus(diary.getGameInfo().getGameStatus())
                .contents(diary.getContent())  // 다이어리 내용 포함
                .imgUrl(diary.getImgUrl())  // 이미지 포함
                .build();
    }

    //점수 변환 로직 (String → int)
    private static int parseScore(String score) {
        try {
            return Integer.parseInt(score);
        } catch (NumberFormatException e) {
            return 0; // 변환 실패 시 기본값 0 반환
        }
    }
}
