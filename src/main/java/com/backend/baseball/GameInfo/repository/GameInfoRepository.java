package com.backend.baseball.GameInfo.repository;

import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.GameInfo.entity.TeamRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GameInfoRepository extends JpaRepository<GameInfo, Long> {
    List<GameInfo> findByGameDate(LocalDate date);

    @Query("SELECT g FROM GameInfo g WHERE (g.team1 = :club OR g.team2 = :club) AND g.gameDate = :gameDate")
    GameInfo findGamesByClubAndDate(@Param("club") String club, @Param("gameDate") LocalDate gameDate);
}
