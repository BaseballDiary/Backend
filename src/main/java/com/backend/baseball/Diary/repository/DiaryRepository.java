package com.backend.baseball.Diary.repository;

import com.backend.baseball.Diary.entity.Diary;
import com.backend.baseball.Diary.enums.ViewType;
import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    boolean existsByGameInfo(GameInfo gameInfo); //야구 일기 중복 검사 메서드

    //특정 연도 및 ViewType(직관/집관)으로 Diary 조회하는 쿼리 메서드 추가
    List<Diary> findByViewTypeAndDateBetween(ViewType viewType, LocalDate startDate, LocalDate endDate);

    List<Diary> findByUserAndViewTypeAndDateBetween(User user, ViewType viewType, LocalDate startDate, LocalDate endDate);

}
