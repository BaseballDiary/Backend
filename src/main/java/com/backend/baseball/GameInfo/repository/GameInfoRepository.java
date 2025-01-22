package com.backend.baseball.GameInfo.repository;

import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.GameInfo.entity.TeamRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameInfoRepository extends JpaRepository<GameInfo, Integer> {
    List<GameInfo> findByYear(String year);
}
