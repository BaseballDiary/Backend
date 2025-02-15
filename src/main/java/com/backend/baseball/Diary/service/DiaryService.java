package com.backend.baseball.Diary.service;

import com.backend.baseball.Diary.dto.*;
import com.backend.baseball.Diary.dto.viewDiary.*;
import com.backend.baseball.Diary.entity.Diary;
import com.backend.baseball.Diary.enums.ViewType;
import com.backend.baseball.Diary.repository.DiaryRepository;
import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.GameInfo.entity.TeamRanking;
import com.backend.baseball.GameInfo.repository.GameInfoRepository;
import com.backend.baseball.GameInfo.repository.TeamRankingRepository;
import com.backend.baseball.Login.User.repository.UserRepository;
import com.backend.baseball.Login.entity.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final GameInfoRepository gameInfoRepository;
    private final UserRepository userRepository;
    private final TeamRankingRepository teamRankingRepository;
    private final HttpSession httpSession;

    //날짜와 유저 클럽을 기반으로 경기 일정 가져오기
    @Transactional
    public GameInfo getGameInfoByDate(String date, HttpSession session) {
        // 1. 세션에서 로그인된 사용자 가져오기
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        // 2. 날짜 변환
        LocalDate gameDate = LocalDate.parse(date);

        // 3. 유저의 클럽 정보 가져오기
        String club = user.getMyClub();

        // 4. 해당 날짜와 클럽에 맞는 경기 찾기
        return gameInfoRepository.findGamesByClubAndDate(club, gameDate)
                .orElseThrow(() -> new IllegalStateException("해당 날짜의 경기 정보를 찾을 수 없습니다."));
    }

    //경기 정보만 있는 Diary에다가 내용, imgUrl저장
    public Optional<Diary> findByGameId(Long gameId) {
        return diaryRepository.findByGameId(gameId);
    }

    @Transactional
    public DiaryResponseDTO updateDiaryContents(Diary diary) {
        diaryRepository.save(diary);
        return DiaryResponseDTO.fromEntity(diary);
    }


    //다이어리 저장
    @Transactional
    public DiaryResponseDTO saveGameToDiary(SaveDiaryRequestDTO request, HttpSession session) {
        // 1. 세션에서 로그인된 사용자 가져오기
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        // 2. gameId를 이용하여 해당 경기 정보 가져오기
        GameInfo gameInfo = gameInfoRepository.findById(request.getGameId())
                .orElseThrow(() -> new IllegalStateException("해당 경기를 찾을 수 없습니다."));

        // 3. Diary 객체 생성
        Diary diary = Diary.builder()
                .user(user)
                .gameInfo(gameInfo)
                .contents(request.getContents() != null ? request.getContents() : "") // 기본값 빈 문자열
                .imgUrls(request.getImgUrls() != null ? request.getImgUrls() : Collections.emptyList())
                .build();

        // 4. Diary 저장
        diary = diaryRepository.save(diary);

        // 5. 저장된 Diary를 ResponseDTO로 변환하여 반환
        return DiaryResponseDTO.fromEntity(diary);
    }

    //일기 수정
    @Transactional
    public DiaryResponseDTO updateDiary(Long diaryId, DiaryUpdateRequestDTO request, Long certificatedId) {
        //해당 일기 찾기
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new EntityNotFoundException("해당 일기를 찾을 수 없습니다."));

        //본인 일기인지 확인
        if (!diary.getUser().getCertificateId().equals(certificatedId)) {
            throw new IllegalStateException("자신의 일기만 수정할 수 있습니다.");
        }

        //일기 내용과 이미지 리스트 업데이트 (null 체크 후 변경)
        if (request.getContent() != null) {
            diary.update(diary.getDate(), diary.getViewType(), request.getContent(), diary.getImgUrls(), diary.getGameInfo());
        }

        if (request.getUploadImages() != null) {
            // 기존 이미지 ID 리스트를 새로운 이미지 ID 리스트로 업데이트
            List<String> imageUrls = request.getUploadImages().stream()
                    .map(id -> "https://example.com/images/" + id) // ID를 실제 URL로 변환
                    .toList();
            diary.update(diary.getDate(), diary.getViewType(), diary.getContents(), imageUrls, diary.getGameInfo());
        }

        //return diaryRepository.save(diary);
        Diary updateDiary = diaryRepository.save(diary);

        return DiaryResponseDTO.fromEntity(updateDiary);
    }

    //일기 삭제
    @Transactional
    public void deleteDiary(Long diaryId, Long certificatedId) {
        //해당 일기 찾기
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new EntityNotFoundException("해당 일기를 찾을 수 없습니다."));

        //본인 일기인지 확인
        if (!diary.getUser().getCertificateId().equals(certificatedId)) {
            throw new IllegalStateException("자신의 일기만 삭제할 수 있습니다.");
        }

        //삭제
        diaryRepository.delete(diary);
    }



    //년도별 스탯 불러오기
    public StatsResponseDTO getStatsByYear(String year, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        String myClub = user.getMyClub();

        List<Diary> diaries = diaryRepository.findByYearAndViewType(year, "onSite");

        long myWins = 0;
        long myLosses = 0;
        long myDraws = 0;

        for (Diary diary : diaries) {
            GameInfo gameInfo = diary.getGameInfo();

            int team1Score = gameInfo.getTeam1Score() != null ? Integer.parseInt(gameInfo.getTeam1Score()) : 0;
            int team2Score = gameInfo.getTeam2Score() != null ? Integer.parseInt(gameInfo.getTeam2Score()) : 0;

            if (myClub.equals(gameInfo.getTeam1())) {
                if (team1Score > team2Score) myWins++;
                else if (team1Score < team2Score) myLosses++;
                else myDraws++;
            } else if (myClub.equals(gameInfo.getTeam2())) {
                if (team2Score > team1Score) myWins++;
                else if (team2Score < team1Score) myLosses++;
                else myDraws++;
            }
        }

        long myGames = diaries.size();
        int myWinRate = myGames > 0 ? (int) ((myWins * 100) / myGames) : 0;

        TeamRanking teamRanking = teamRankingRepository.findByYearAndTeamName(year, myClub)
                .orElseThrow(() -> new EntityNotFoundException("Team ranking not found"));

        return new StatsResponseDTO(
                new MyStatsDTO((int) myWins, (int) myLosses, (int) myDraws, (int) myGames, myWinRate),
                new TeamStatsDTO(
                        Integer.parseInt(teamRanking.getWin()),
                        Integer.parseInt(teamRanking.getLose()),
                        Integer.parseInt(teamRanking.getTie()),
                        Integer.parseInt(teamRanking.getGameNum()),
                        (int) (Double.parseDouble(teamRanking.getWinningRate()) * 100)
                )
        );
    }

    //직관일기 리스트 불러오기
    public List<OnSiteDiaryDTO> getOnSiteDiaries(String year, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        String myClub = user.getMyClub();

        List<Diary> diaries = diaryRepository.findByYearAndViewType(year, "onSite");

        return diaries.stream().map(diary -> {
            GameInfo gameInfo = diary.getGameInfo();

            int team1Score = gameInfo.getTeam1Score() != null ? Integer.parseInt(gameInfo.getTeam1Score()) : 0;
            int team2Score = gameInfo.getTeam2Score() != null ? Integer.parseInt(gameInfo.getTeam2Score()) : 0;

            boolean isWin = (myClub.equals(gameInfo.getTeam1()) && team1Score > team2Score) ||
                    (myClub.equals(gameInfo.getTeam2()) && team2Score > team1Score);

            return new OnSiteDiaryDTO(
                    diary.getDiaryId(),
                    gameInfo.getPlace(),
                    myClub,
                    myClub.equals(gameInfo.getTeam1()) ? team1Score : team2Score,
                    myClub.equals(gameInfo.getTeam1()) ? gameInfo.getTeam2() : gameInfo.getTeam1(),
                    myClub.equals(gameInfo.getTeam1()) ? team2Score : team1Score,
                    isWin,
                    gameInfo.getTime(),
                    gameInfo.getGameDate().toString(),
                    gameInfo.getGameDate().getDayOfWeek().toString()
            );
        }).toList();
    }

    //집관일기 리스트 불러오기
    public List<AtHomeDiaryDTO> getAtHomeDiaries(String year, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        String myClub = user.getMyClub();

        List<Diary> diaries = diaryRepository.findByYearAndViewType(year, "atHome");

        return diaries.stream().map(diary -> {
            GameInfo gameInfo = diary.getGameInfo();

            int team1Score = gameInfo.getTeam1Score() != null ? Integer.parseInt(gameInfo.getTeam1Score()) : 0;
            int team2Score = gameInfo.getTeam2Score() != null ? Integer.parseInt(gameInfo.getTeam2Score()) : 0;

            boolean isWin = (myClub.equals(gameInfo.getTeam1()) && team1Score > team2Score) ||
                    (myClub.equals(gameInfo.getTeam2()) && team2Score > team1Score);

            return new AtHomeDiaryDTO(
                    diary.getDiaryId(),
                    myClub,
                    myClub.equals(gameInfo.getTeam1()) ? team1Score : team2Score,
                    myClub.equals(gameInfo.getTeam1()) ? gameInfo.getTeam2() : gameInfo.getTeam1(),
                    myClub.equals(gameInfo.getTeam1()) ? team2Score : team1Score,
                    isWin,
                    gameInfo.getGameDate().toString(),
                    gameInfo.getGameDate().getDayOfWeek().toString()
            );
        }).toList();
    }


    //야구일기 단건 조회
    public DiaryDetailDTO getDiaryById(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new EntityNotFoundException("Diary not found"));

        GameInfo gameInfo = diary.getGameInfo();
        String myClub = diary.getUser().getMyClub();

        int team1Score = gameInfo.getTeam1Score() != null ? Integer.parseInt(gameInfo.getTeam1Score()) : 0;
        int team2Score = gameInfo.getTeam2Score() != null ? Integer.parseInt(gameInfo.getTeam2Score()) : 0;

        boolean isWin = (myClub.equals(gameInfo.getTeam1()) && team1Score > team2Score) ||
                (myClub.equals(gameInfo.getTeam2()) && team2Score > team1Score);

        return new DiaryDetailDTO(
                diary.getDate().toString(),
                diary.getDay(),
                gameInfo.getTime(),
                gameInfo.getPlace(),
                isWin,
                myClub,
                myClub.equals(gameInfo.getTeam1()) ? team1Score : team2Score,
                myClub.equals(gameInfo.getTeam1()) ? gameInfo.getTeam2() : gameInfo.getTeam1(),
                myClub.equals(gameInfo.getTeam1()) ? team2Score : team1Score,
                diary.getImgUrls(),
                diary.getContents()
        );
    }
}
