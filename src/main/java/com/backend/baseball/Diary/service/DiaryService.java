package com.backend.baseball.Diary.service;

import com.backend.baseball.Diary.dto.AddDiaryRequest;
import com.backend.baseball.Diary.entity.Diary;
import com.backend.baseball.Diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    //일기 추가 메서드
    public Diary save(AddDiaryRequest request){
        return diaryRepository.save(request.toEntity());
    }
}
