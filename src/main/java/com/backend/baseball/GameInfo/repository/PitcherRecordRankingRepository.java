package com.backend.baseball.GameInfo.repository;

import com.backend.baseball.GameInfo.entity.PitcherRecordRanking;
import com.backend.baseball.GameInfo.entity.TeamRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PitcherRecordRankingRepository extends JpaRepository<PitcherRecordRanking, Long> {
    List<PitcherRecordRanking> findByYear(String year);
}
