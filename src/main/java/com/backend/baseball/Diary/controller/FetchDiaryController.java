package com.backend.baseball.Diary.controller;

import com.backend.baseball.Diary.dto.FetchDiary.ViewDiaryByYearResponseDTO;
import com.backend.baseball.Diary.entity.Diary;
import com.backend.baseball.Diary.enums.ViewType;
import com.backend.baseball.Diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/diary")
public class FetchDiaryController {
    private final DiaryRepository diaryRepository;

    @GetMapping("/{year}/view")
    public ResponseEntity<?> getDiaryByYearAndViewType(
            @PathVariable("year") int year,
            @RequestParam("status") String status) {

        // ViewType 매핑
        ViewType viewType;
        try {
            viewType = ViewType.valueOf(status);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"status\": 400, \"message\": \"Invalid status value. Use 'onSite' or 'atHome'.\"}");
        }

        // 해당 년도의 해당 viewType(직관/집관) 일기 조회
        List<Diary> diaries = diaryRepository.findByViewTypeAndDateBetween(
                viewType,
                LocalDate.of(year, 1, 1),
                LocalDate.of(year, 12, 31)
        );

        // DTO로 변환
        List<ViewDiaryByYearResponseDTO> diaryList = diaries.stream()
                .map(ViewDiaryByYearResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(
                Map.of("data", Map.of("diaryList", diaryList))
        );
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<?> getDiaryById(@PathVariable("diaryId") Long diaryId) {
        Optional<Diary> diaryOpt = diaryRepository.findById(diaryId);

        if (diaryOpt.isEmpty()) {
            return ResponseEntity.status(404).body("{\"status\": 404, \"message\": \"해당 diaryId에 대한 일기가 존재하지 않습니다.\"}");
        }

        Diary diary = diaryOpt.get();

        //JSON 형식 맞추기 위해 DTO 변환
        ViewDiaryByYearResponseDTO diaryResponse = ViewDiaryByYearResponseDTO.builder()
                .date(diary.getDate().toString())
                .dayOfWeek(diary.getDay())
                .time(diary.getGameInfo().getTime())
                .stadium(diary.getGameInfo().getPlace())
                .result(diary.getGameInfo().getGameStatus()) // ✅ 승리, 패배, 무승부 그대로 반환
                .myTeam(diary.getGameInfo().getTeam1())
                .myScore(Integer.parseInt(diary.getGameInfo().getTeam1Score())) // String → int 변환
                .opponentTeam(diary.getGameInfo().getTeam2())
                .opponentScore(Integer.parseInt(diary.getGameInfo().getTeam2Score())) // String → int 변환
                .uploadImages(diary.getImgUrls()) // 이미지 리스트
                .content(diary.getContents())
                .build();

        //프론트엔드 응답 형식에 맞게 JSON 변환
        return ResponseEntity.ok().body(
                Map.of("data", diaryResponse)
        );
    }

}
