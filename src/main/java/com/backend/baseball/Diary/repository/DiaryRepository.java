package com.backend.baseball.Diary.repository;

import com.backend.baseball.Diary.entity.Diary;
import com.backend.baseball.GameInfo.entity.GameInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    boolean existsByGameInfo(GameInfo gameInfo); //야구 일기 중복 검사 메서드
}
