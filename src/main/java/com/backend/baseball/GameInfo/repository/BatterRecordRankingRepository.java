package com.backend.baseball.GameInfo.repository;

import com.backend.baseball.GameInfo.entity.BatterRecordRanking;
import com.backend.baseball.GameInfo.entity.TeamRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatterRecordRankingRepository extends JpaRepository<BatterRecordRanking, Long> {
    List<BatterRecordRanking> findByYear(String year);
}
