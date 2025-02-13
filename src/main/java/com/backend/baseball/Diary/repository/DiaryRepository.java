package com.backend.baseball.Diary.repository;

import com.backend.baseball.Diary.entity.Diary;
import com.backend.baseball.Diary.enums.ViewType;
import com.backend.baseball.Login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    // ✅ 사용자가 특정 연도에 작성한 일기 조회 (year은 int 타입)
    @Query("SELECT d FROM Diary d WHERE d.user = :user AND FUNCTION('YEAR', d.date) = :year")
    List<Diary> findByUserAndYear(@Param("user") User user, @Param("year") int year);

    // ✅ 특정 연도 및 공개 유형(ViewType)에 따른 일기 조회 (year은 String 타입)
    @Query("SELECT d FROM Diary d WHERE FUNCTION('YEAR', d.date) = CAST(:year AS int) AND d.viewType = :viewType")
    List<Diary> findByYearAndViewType(@Param("year") String year, @Param("viewType") String viewType);
}
