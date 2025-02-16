package com.backend.baseball.GameInfo.repository;

import com.backend.baseball.GameInfo.entity.BatterRanking;
import com.backend.baseball.GameInfo.entity.TeamRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatterRankingRepository extends JpaRepository<BatterRanking, Integer> {
    List<BatterRanking> findByYear(String year);
}
