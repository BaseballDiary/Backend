package com.backend.baseball.Diary.repository;

import com.backend.baseball.Diary.entity.Diary;
import com.backend.baseball.Login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    @Query("SELECT d FROM Diary d WHERE d.user = :user AND YEAR(d.date) = :year")
    List<Diary> findByUserAndYear(User user, int year);
}
