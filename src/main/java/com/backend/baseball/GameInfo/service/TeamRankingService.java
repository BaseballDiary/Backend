package com.backend.baseball.GameInfo.service;

import com.backend.baseball.GameInfo.crawling.CrawlingTeamRanking;
import com.backend.baseball.GameInfo.entity.TeamRanking;
import com.backend.baseball.GameInfo.repository.TeamRankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamRankingService {

    private final TeamRankingRepository teamRankingRepository;
    private final CrawlingTeamRanking crawlingTeamRanking;

    public List<TeamRanking> getTeamRanking(String year){
        List<TeamRanking> teamRanking = teamRankingRepository.findByYear(year);
        if(teamRanking == null){
            teamRanking = crawlingTeamRanking.crawling(year); //크롤링 작업 시작
        }
        return teamRanking;
    }
}
