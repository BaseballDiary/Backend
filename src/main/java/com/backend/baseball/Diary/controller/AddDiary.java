package com.backend.baseball.Diary.controller;

import com.backend.baseball.Diary.dto.AddDiaryRequest;
import com.backend.baseball.Diary.dto.UpdateDiaryRequest;
import com.backend.baseball.Diary.entity.Diary;
import com.backend.baseball.Diary.service.DiaryService;
import com.backend.baseball.GameInfo.entity.GameInfo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController //HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class AddDiary implements AddDiaryDocs{

    private final DiaryService diaryService;

    //유저 클럽 + 날짜로 게임정보 받아오기
    @GetMapping("/diary/create/fetchgame")
    public ResponseEntity<?> fetchGame(@RequestParam("date") String date, HttpSession session) {
        GameInfo gameInfo = diaryService.getGameInfoByDate(date, session);
        return ResponseEntity.ok(gameInfo);
    }

    //HTTP 메서드가 POST일 때 전달받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/diary/create")
    //@RequestBody로 요청 본문 값 매핑
    public ResponseEntity<Diary> addDiary(@RequestBody AddDiaryRequest request, HttpSession session){
        Diary savedDiary = diaryService.save(request, session);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedDiary);
    }

    // 일기 수정 (gameId 변경 가능)
    @PutMapping("/diary/{diaryId}")
    public ResponseEntity<Diary> updateDiary(
            @PathVariable Long diaryId,
            @RequestBody UpdateDiaryRequest request,
            HttpSession session) {
        Diary updatedDiary = diaryService.updateDiary(diaryId, request, session);
        return ResponseEntity.ok(updatedDiary);
    }

    //일기 삭제
    @DeleteMapping("/diary/{diaryId}")
    public ResponseEntity<Void> deleteDiary(
            @PathVariable Long diaryId,
            HttpSession session) {
        diaryService.deleteDiary(diaryId, session);
        return ResponseEntity.noContent().build();
    }


}
