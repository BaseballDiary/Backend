package com.backend.baseball.Diary.controller;

import com.backend.baseball.Diary.service.DiaryService;
import com.backend.baseball.Diary.service.FetchDiaryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/diary")
public class FetchDiary {

    private final DiaryService diaryService;
    private final FetchDiaryService fetchDiaryService;

    @GetMapping("/{year}")
    public ResponseEntity<DiaryResponse> getDiariesByYear(
            @PathVariable("year") int year, HttpSession session) {
        DiaryResponse response = fetchDiaryService.getDiariesByYear(year, session);
        return ResponseEntity.ok(response);
    }
}