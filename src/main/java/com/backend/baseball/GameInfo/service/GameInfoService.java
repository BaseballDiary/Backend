package com.backend.baseball.GameInfo.service;

import com.backend.baseball.Diary.dto.GameResponseDTO;
import com.backend.baseball.GameInfo.crawling.CrawlingGameInfo;
import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.GameInfo.entity.TeamRanking;
import com.backend.baseball.GameInfo.repository.GameInfoRepository;
import com.backend.baseball.Login.User.repository.UserRepository;
import com.backend.baseball.Login.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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




    /**
     * ✅ 특정 날짜의 모든 경기 정보 조회
     */
    public List<GameResponseDTO> getGameInfoByDate(String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        List<GameInfo> todayGameInfo = gameInfoRepository.findByGameDate(date);

        if (todayGameInfo.isEmpty()) {
            todayGameInfo = crawlingGameInfo.crawling(dateString);
        }

        return todayGameInfo.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * ✅ `certificated_id`를 기반으로 내 구단이 포함된 경기만 조회
     */
    public List<GameResponseDTO> getUserTeamGameInfoByDate(String dateString, Long certificatedId) {
        LocalDate date = LocalDate.parse(dateString);

        // 1️⃣ `certificated_id`를 통해 사용자의 구단 정보 가져오기
        Optional<User> userOptional = userRepository.findById(certificatedId);
        if (userOptional.isEmpty()) {
            throw new IllegalStateException("사용자를 찾을 수 없습니다.");
        }

        String myClub = userOptional.get().getMyClub(); // 사용자의 구단

        // 2️⃣ 특정 날짜의 경기 중 내 구단이 포함된 경기만 조회
        List<GameInfo> games = gameInfoRepository.findByGameDate(date)
                .stream()
                .filter(game -> game.getTeam1().equals(myClub) || game.getTeam2().equals(myClub)) // 내 구단이 포함된 경기만 필터링
                .collect(Collectors.toList());

        // 3️⃣ DTO 변환 후 반환
        return games.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * ✅ GameInfo → GameResponseDTO 변환
     */
    private GameResponseDTO convertToDTO(GameInfo gameInfo) {
        DayOfWeek dayOfWeek = gameInfo.getGameDate().getDayOfWeek();
        String day = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN);

        return GameResponseDTO.of(
                gameInfo.getGameCertificateId(),
                gameInfo.getTeam1(),
                gameInfo.getTeam2(),
                Integer.parseInt(gameInfo.getTeam1Score()),
                Integer.parseInt(gameInfo.getTeam2Score()),
                gameInfo.getGameDate(),
                day,
                gameInfo.getTime(),
                gameInfo.getPlace(),
                gameInfo.getGameStatus()
        );
    }


}



