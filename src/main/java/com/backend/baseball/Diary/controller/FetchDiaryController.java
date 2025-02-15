package com.backend.baseball.Diary.controller;

import com.backend.baseball.Diary.dto.viewDiary.AtHomeDiaryDTO;
import com.backend.baseball.Diary.dto.viewDiary.DiaryDetailDTO;
import com.backend.baseball.Diary.dto.viewDiary.OnSiteDiaryDTO;
import com.backend.baseball.Diary.service.DiaryService;
import com.backend.baseball.Login.User.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//야구일기 조회 컨트롤러
@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
@Tag(name = "Diary API", description = "야구 일기 불러오기")

public class FetchDiaryController{
    private final DiaryService diaryService;
    private final UserService userService;
    private final HttpSession httpSession;


    //직관, 집관일기 리스트
    @GetMapping("/{year}/view")
    public ResponseEntity<?> getDiaries(
            @PathVariable String year,
            @RequestParam String status,
            HttpSession session) {

        if ("onSite".equals(status)) {
            List<OnSiteDiaryDTO> response = diaryService.getOnSiteDiaries(year, session);
            return ResponseEntity.ok(response);
        } else if ("atHome".equals(status)) {
            List<AtHomeDiaryDTO> response = diaryService.getAtHomeDiaries(year, session);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body("Invalid status parameter");
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<DiaryDetailDTO> getDiary(@PathVariable Long diaryId) {
        DiaryDetailDTO response = diaryService.getDiaryById(diaryId);
        return ResponseEntity.ok(response);
    }
}
