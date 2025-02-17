package com.backend.baseball.Diary.controller;

import com.backend.baseball.Diary.dto.CreateDiary.DiaryAddRequestDTO;
import com.backend.baseball.Diary.dto.CreateDiary.GameInfoResponseDTO;
import com.backend.baseball.Diary.dto.CreateDiary.SaveGameRequestDTO;
import com.backend.baseball.Diary.entity.Diary;
import com.backend.baseball.Diary.enums.ViewType;
import com.backend.baseball.Diary.etc.DateUtils;
import com.backend.baseball.Diary.repository.DiaryRepository;
import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.GameInfo.repository.GameInfoRepository;
import com.backend.baseball.User.entity.User;
import com.backend.baseball.User.helper.AccountHelper;
import com.backend.baseball.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/diary")
public class CreateDiaryController {

    private final GameInfoRepository gameInfoRepository;
    private final AccountHelper accountHelper;
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    //프론트에서 날짜 보내주면 날짜 + 내 구단 조합해서 경기 일정 보내주기
    @PostMapping("/create/fetchgame")
    public ResponseEntity<?> fetchGame(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateString, HttpServletRequest req) {
        //1. String -> LocalDate 변환
        LocalDate gameDate;
        try {
            gameDate = LocalDate.parse(dateString);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid date format. Use YYYY-MM-DD.");
        }

        //2. 현재 로그인한 사용자의 myClub 조회
        Long memberId = accountHelper.getMemberId(req);
        if (memberId == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        //3. 사용자의 myClub 가져오기
        Optional<User> userOptional = userRepository.findById(memberId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body("사용자를 찾을 수 없습니다.");
        }

        String myClub = userOptional.get().getMyClub();

        Optional<GameInfo> gameInfo = gameInfoRepository.findByGameDateAndTeam(gameDate, myClub);

        return gameInfo
                .map(info -> {
                    // ✅ 한글 요일 변환 후 DTO 생성
                    GameInfoResponseDTO responseDTO = GameInfoResponseDTO.fromEntity(info);
                    responseDTO.setDay(DateUtils.getKoreanDay(gameDate)); // 한글 요일 설정
                    return ResponseEntity.ok(responseDTO);
                })
                .orElse(ResponseEntity.status(404).build());  // 404 처리 (No matching game found)
    }


    //경기 정보를 Diary에 저장하는 API
    @PostMapping("/create/saveGame")
    public ResponseEntity<?> saveGame(@RequestBody SaveGameRequestDTO saveGameRequest, HttpServletRequest req) {
        //현재 로그인한 사용자 확인
        Long memberId = accountHelper.getMemberId(req);
        if (memberId == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        Optional<User> userOptional = userRepository.findById(memberId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body("사용자를 찾을 수 없습니다.");
        }
        User user = userOptional.get();

        //GameInfo 엔티티 조회
        Optional<GameInfo> gameInfoOptional = gameInfoRepository.findById(saveGameRequest.getGameId());
        if (gameInfoOptional.isEmpty()) {
            return ResponseEntity.status(404).body("경기 정보를 찾을 수 없습니다.");
        }
        GameInfo gameInfo = gameInfoOptional.get();

        //중복 검사: 해당 경기에 대한 일기가 이미 존재하는지 확인
        if (diaryRepository.existsByGameInfo(gameInfo)) {
            return ResponseEntity.status(400).body("이미 해당 경기에 대한 일기가 존재합니다.");
        }

        //Diary 저장
        Diary diary = Diary.builder()
                .date(gameInfo.getGameDate()) // 경기 날짜
                .day(saveGameRequest.getDay()) // 프론트에서 전달된 요일
                .viewType(ViewType.atHome) // 기본값으로 atHome 설정
                .contents("")  //초기에는 내용 비우기
                .imgUrls(null)  //초기에는 이미지 URL 비우기
                .gameInfo(gameInfo)
                .user(user)
                .build();

        Diary savedDiary = diaryRepository.save(diary);

        // 4️⃣ 저장된 diaryId 반환
        return ResponseEntity.ok(savedDiary.getDiaryId());
    }


    //일기 저장
    @PostMapping("/create")
    public ResponseEntity<?> createOrUpdateDiary(@RequestBody DiaryAddRequestDTO request) {
        // 1️⃣ gameId와 certificateId를 사용해 기존 Diary 찾기
        Optional<Diary> diaryOpt = diaryRepository.findByGameInfoGameCertificateIdAndUserCertificateId(
                request.getGameId(), request.getCertificateId()
        );

        if (diaryOpt.isPresent()) {
            // 2️⃣ 기존 Diary 업데이트
            Diary diary = diaryOpt.get();
            diary.setContents(request.getContents() != null ? request.getContents() : "베볼리");
            diary.setImgUrls(request.getImgUrls() != null && !request.getImgUrls().isEmpty() ? request.getImgUrls() :
                    List.of("https://s3-alpha-sig.figma.com/img/8ee5/0c2e/b058dc79ca1625d68efd4664511165e3"));

            diaryRepository.save(diary);
            return ResponseEntity.ok("{\"status\": 200, \"message\": \"일기를 성공적으로 업데이트했습니다.\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"status\": 400, \"message\": \"해당 gameId와 certificateId에 대한 일기가 존재하지 않습니다.\"}");
        }
    }


}
