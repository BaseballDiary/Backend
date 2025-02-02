package com.backend.baseball.Diary.repository;

import com.backend.baseball.Diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
