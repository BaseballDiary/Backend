package com.backend.baseball.GameInfo.service;

import com.backend.baseball.GameInfo.crawling.CrawlingGameInfo;
import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.GameInfo.entity.TeamRanking;
import com.backend.baseball.GameInfo.repository.GameInfoRepository;
import com.backend.baseball.User.repository.UserRepository;
import com.backend.baseball.User.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GameInfoService {

    private final GameInfoRepository gameInfoRepository;
    private final CrawlingGameInfo crawlingGameInfo;
    private final UserRepository userRepository;

    public List<GameInfo> getTodayGameInfo(String dateString){
        LocalDate date = LocalDate.parse(dateString);
        List<GameInfo> todayGameInfo = gameInfoRepository.findByGameDate(date);
        if(todayGameInfo.isEmpty()){
            todayGameInfo = crawlingGameInfo.crawling(dateString);
        }
        return todayGameInfo;
    }









}



