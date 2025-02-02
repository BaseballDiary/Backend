package com.backend.baseball.GameInfo.service;

import com.backend.baseball.GameInfo.crawling.CrawlingGameInfo;
import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.GameInfo.entity.TeamRanking;
import com.backend.baseball.GameInfo.repository.GameInfoRepository;
import com.backend.baseball.Login.User.repository.UserRepository;
import com.backend.baseball.Login.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public GameInfo getMyClubGameInfo(String name, String dateString){
        User user = userRepository.findByNickname(name);
        if(user == null){
            return null;
        }
        String club = user.getMyClub();
        LocalDate date = LocalDate.parse(dateString);

        return gameInfoRepository.findGamesByClubAndDate(club, date)
                .orElse(GameInfo.builder().gameStatus("경기 없음").build());
        //세연 수정
        //GameInfo myClubGameInfo = gameInfoRepository.findGamesByClubAndDate(club, date);
        //if(myClubGameInfo == null){
        //   return GameInfo.builder().gameStatus("경기 없음").build();
        //}
        //return myClubGameInfo;
    }
}
