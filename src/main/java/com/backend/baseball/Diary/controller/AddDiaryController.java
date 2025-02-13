package com.backend.baseball.Diary.controller;

import com.backend.baseball.Diary.dto.DiaryResponseDTO;
import com.backend.baseball.Diary.dto.DiaryUpdateRequestDTO;
import com.backend.baseball.Diary.dto.SaveDiaryRequestDTO;
import com.backend.baseball.Diary.service.DiaryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
@Tag(name = "Diary API", description = "야구 일기 생성/수정/삭제 API")
public class AddDiaryController implements AddDiaryControllerDocs{

    private final DiaryService diaryService;

    // 일기 저장 API
    @PostMapping("/create")
    public ResponseEntity<DiaryResponseDTO> saveDiary(
            @RequestBody SaveDiaryRequestDTO request, //DTO 변경
            HttpSession session) {

        DiaryResponseDTO response = diaryService.saveGameToDiary(request, session);
        return ResponseEntity.ok(response);
    }

    //일기 수정 API
    @PutMapping("/{diaryId}")
    public ResponseEntity<DiaryResponseDTO> updateDiary(
            @PathVariable Long diaryId,
            @RequestBody DiaryUpdateRequestDTO request,
            HttpSession session) {

        Long certificatedId = (Long) session.getAttribute("certificated_id");

        if (certificatedId == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        DiaryResponseDTO updatedDiary = diaryService.updateDiary(diaryId, request, certificatedId);
        return ResponseEntity.ok(updatedDiary);
    }


    //일기 삭제 API

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Map<String, Object>> deleteDiary(
            @PathVariable Long diaryId,
            HttpSession session) {

        Long certificatedId = (Long) session.getAttribute("certificated_id");

        if (certificatedId == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        diaryService.deleteDiary(diaryId, certificatedId);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "일기를 성공적으로 삭제했습니다.");

        return ResponseEntity.ok(response);
    }
}
