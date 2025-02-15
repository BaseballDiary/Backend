package com.backend.baseball.Diary.controller;

import com.backend.baseball.Diary.dto.DiaryResponseDTO;
import com.backend.baseball.Diary.dto.DiaryUpdateRequestDTO;
import com.backend.baseball.Diary.dto.SaveDiaryRequestDTO;
import com.backend.baseball.Diary.entity.Diary;
import com.backend.baseball.Diary.service.DiaryService;
import com.backend.baseball.User.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
@Tag(name = "Diary API", description = "야구 일기 생성/수정/삭제 API")
public class AddDiaryController implements AddDiaryControllerDocs{

    private final DiaryService diaryService;

    // 일기 저장 API

    @PostMapping("/create")
    public ResponseEntity<DiaryResponseDTO> saveDiary(
            @RequestBody SaveDiaryRequestDTO request, // gameId, contents, imgUrls 포함
            HttpSession session) {

        // 1. 세션에서 로그인한 사용자 가져오기
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        // 2. gameId 확인
        Long gameId = request.getGameId();
        if (gameId == null) {
            return ResponseEntity.badRequest().body(null); // gameId가 없으면 400 응답
        }

        // 3. 기존 저장된 일기 찾기
        Optional<Diary> diaryOpt = diaryService.findByGameId(gameId);
        if (diaryOpt.isEmpty()) {
            return ResponseEntity.status(404).body(null); // 해당 gameId의 일기가 없으면 404 응답
        }

        Diary diary = diaryOpt.get();

        // 4. 내용 및 이미지 리스트 업데이트
        if (request.getContents() != null) {
            diary.setContents(request.getContents());
        }

        if (request.getImgUrls() != null) {
            diary.setImgUrls(request.getImgUrls());
        }

        // 5. 변경된 내용 저장
        DiaryResponseDTO response = diaryService.updateDiaryContents(diary);
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
