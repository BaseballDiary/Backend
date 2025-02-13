package com.backend.baseball.Diary.controller;

import com.backend.baseball.Diary.dto.DiaryResponseDTO;
import com.backend.baseball.Diary.dto.FetchGameByDateDTO;
import com.backend.baseball.Diary.dto.GameResponseDTO;
import com.backend.baseball.Diary.entity.Diary;
import com.backend.baseball.GameInfo.repository.GameInfoRepository;
import com.backend.baseball.Diary.service.DiaryService;
import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.GameInfo.service.GameInfoService;
import com.backend.baseball.Diary.dto.SaveDiaryRequestDTO;
import com.backend.baseball.Login.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "Diary API", description = "야구 일기 생성 시 야구경기 정보 가져오기")
@RequestMapping("/diary")
//프론트에서 년-월-일 보내면 해당하는 경기 정보 보내주기
public class FetchGameByDateController{

    private final GameInfoService gameInfoService;
    private final DiaryService diaryService;
    private final GameInfoRepository gameInfoRepository;


    //야구일기 작성버튼 클릭 시 내구단 보내기
    @GetMapping("/fetchMyClub")
    public ResponseEntity<Map<String, String>> fetchMyClub(HttpSession session) {
        // 세션에서 로그인한 사용자 정보 가져오기
        User user = (User) session.getAttribute("loginUser");

        if (user == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        // 사용자의 구단 정보 반환
        String myClub = user.getMyClub();
        return ResponseEntity.ok(Map.of("myClub", myClub));
    }


    //로그인한 사용자의 `certificated_id`를 통해 내 구단이 포함된 경기 일정만 조회
    @PostMapping("/create/fetchgame")
    public ResponseEntity<List<GameResponseDTO>> fetchUserTeamGamesByDate(
            @RequestBody FetchGameByDateDTO request,
            HttpSession session) {

        //1. 세션에서 로그인한 사용자의 `certificated_id` 가져오기
        Long certificatedId = (Long) session.getAttribute("certificated_id");

        if (certificatedId == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        //2. 특정 날짜의 경기 중, 사용자의 구단이 포함된 경기만 필터링하여 반환
        List<GameResponseDTO> games = gameInfoService.getUserTeamGameInfoByDate(request.getGameDate().toString(), certificatedId);
        return ResponseEntity.ok(games);
    }

    @PostMapping("/create/saveGame")
    public ResponseEntity<DiaryResponseDTO> fetchGameAndSaveDiary(
            @RequestBody FetchGameByDateDTO request,
            HttpSession session) {

        //1.세션에서 로그인한 사용자 가져오기
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        //2. 사용자의 구단 정보 가져오기
        String userClub = user.getMyClub();

        //3. 날짜와 구단 정보로 경기 찾기
        Optional<GameInfo> gameInfoOpt = gameInfoRepository.findGamesByClubAndDate(userClub, request.getGameDate());

        if (gameInfoOpt.isEmpty()) {
            throw new IllegalStateException("해당 날짜와 구단에 맞는 경기 정보를 찾을 수 없습니다.");
        }

        GameInfo gameInfo = gameInfoOpt.get(); //경기 정보 가져오기

        //4. SaveDiaryRequestDTO 객체 생성
        SaveDiaryRequestDTO saveRequest = new SaveDiaryRequestDTO();
        saveRequest.setGameId(gameInfo.getGameCertificateId()); //gameId 설정
        saveRequest.setContents("");  // 기본값 설정
        saveRequest.setImgUrl("");  // 기본값 설정

        //5.일기 저장
        DiaryResponseDTO savedDiary = diaryService.saveGameToDiary(saveRequest, session);
        return ResponseEntity.ok(savedDiary);    }
}