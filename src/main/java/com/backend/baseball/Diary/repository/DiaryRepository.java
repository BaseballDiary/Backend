package com.backend.baseball.Diary.repository;

import com.backend.baseball.Diary.entity.Diary;
import com.backend.baseball.GameInfo.entity.GameInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    boolean existsByGameInfo(GameInfo gameInfo); //야구 일기 중복 검사 메서드

    //certificateId와 gameId로 기존 일기 찾기
    Optional<Diary> findByGameInfoGameCertificateIdAndUserCertificateId(Long gameId, Long certificateId);

}
