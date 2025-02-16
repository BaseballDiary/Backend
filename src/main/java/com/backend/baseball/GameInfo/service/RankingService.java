package com.backend.baseball.GameInfo.service;

import com.backend.baseball.GameInfo.crawling.*;
import com.backend.baseball.GameInfo.entity.*;
import com.backend.baseball.GameInfo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final TeamRankingRepository teamRankingRepository;
    private final CrawlingTeamRanking crawlingTeamRanking;
    private final PitcherRankingRepository pitcherRankingRepository;
    private final CrawlingPitcherRanking crawlingPitcherRanking;
    private final PitcherRecordRankingRepository pitcherRecordRankingRepository;
    private final CrawlingPitcherRecordRanking crawlingPitcherRecordRanking;
    private final BatterRankingRepository batterRankingRepository;
    private final CrawlingBatterRanking crawlingBatterRanking;
    private final BatterRecordRankingRepository batterRecordRankingRepository;
    private final CrawlingBatterRecordRanking crawlingBatterRecordRanking;

    public List<TeamRanking> getTeamRanking(String year){
        List<TeamRanking> teamRanking = teamRankingRepository.findByYear(year);
        if(teamRanking.isEmpty()){
            teamRanking = crawlingTeamRanking.crawling(year);
        }
        return teamRanking;
    }

    public List<PitcherRanking> getPitcherRanking(String year){
        List<PitcherRanking> pitcherRanking = pitcherRankingRepository.findByYear(year);
        if(pitcherRanking.isEmpty()){
            pitcherRanking = crawlingPitcherRanking.crawling(year);
        }
        return pitcherRanking;
    }

    public List<PitcherRecordRanking> getPitcherRecordRanking(String year){
        List<PitcherRecordRanking> pitcherRecordRanking = pitcherRecordRankingRepository.findByYear(year);
        if(pitcherRecordRanking.isEmpty()){
            pitcherRecordRanking = crawlingPitcherRecordRanking.crawling(year);
        }
        return pitcherRecordRanking;
    }

    public List<BatterRanking> getBatterRanking(String year){
        List<BatterRanking> batterRanking = batterRankingRepository.findByYear(year);
        if(batterRanking.isEmpty()){
            batterRanking = crawlingBatterRanking.crawling(year);
        }
        return batterRanking;
    }

    public List<BatterRecordRanking> getBatterRecordRanking(String year){
        List<BatterRecordRanking> batterRecordRanking = batterRecordRankingRepository.findByYear(year);
        if(batterRecordRanking.isEmpty()){
            batterRecordRanking = crawlingBatterRecordRanking.crawling(year);
        }
        return batterRecordRanking;
    }
}
