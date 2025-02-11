package com.backend.baseball.Diary.controller;

import com.backend.baseball.Diary.dto.DiaryResponseDTO;
import com.backend.baseball.Diary.dto.SaveDiaryRequestDTO; // ✅ DTO 변경
import com.backend.baseball.Diary.service.DiaryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddDiaryController {

    private final DiaryService diaryService;

    // 일기 저장 API
    @PostMapping("/diary/create")
    public DiaryResponseDTO saveDiary(
            @RequestBody SaveDiaryRequestDTO request, //DTO 변경
            HttpSession session) {

        return diaryService.saveGameToDiary(request, session); //이제 일치함
    }

    //일기 수정 API

    //일기 삭제 API
}
