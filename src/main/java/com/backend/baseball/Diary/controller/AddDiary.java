package com.backend.baseball.Diary.controller;

import com.backend.baseball.Diary.dto.AddDiaryRequest;
import com.backend.baseball.Diary.entity.Diary;
import com.backend.baseball.Diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@RestController //HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class AddDiary {

    private final DiaryService diaryService;

    //HTTP 메서드가 POST일 때 전달받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/diary/create")
    //@RequestBody로 요청 본문 값 매핑
    public ResponseEntity<Diary> addDiary(@RequestBody AddDiaryRequest request){
        Diary savedDiary = diaryService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedDiary);
    }

}
