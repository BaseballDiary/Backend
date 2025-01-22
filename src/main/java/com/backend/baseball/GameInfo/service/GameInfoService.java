package com.backend.baseball.GameInfo.service;

import com.backend.baseball.GameInfo.crawling.CrawlingGameInfo;
import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.GameInfo.entity.TeamRanking;
import com.backend.baseball.GameInfo.repository.GameInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameInfoService {

    private final GameInfoRepository gameInfoRepository;
    private final CrawlingGameInfo crawlingGameInfo;

    public List<GameInfo> getTodayGameInfo(String date){
        List<GameInfo> todayGameInfo = gameInfoRepository.findByYear(date);
        if(todayGameInfo == null){
            todayGameInfo = crawlingGameInfo.crawling(date);
        }
        return todayGameInfo;
    }

    public List<GameInfo> getMyClubGameInfo(String date){
        List<GameInfo> todayGameInfo = gameInfoRepository.findByYear(date);
        if(todayGameInfo == null){
            todayGameInfo = crawlingGameInfo.crawling(date);
        }
        return todayGameInfo;
    }
}
