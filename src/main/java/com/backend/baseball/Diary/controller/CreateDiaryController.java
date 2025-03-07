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
public class CreateDiaryController extends CreateDiaryControllerDocs{

    private final GameInfoRepository gameInfoRepository;
    private final AccountHelper accountHelper;
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    //프론트에서 날짜 보내주면 날짜 + 내 구단 조합해서 경기 일정 보내주기
    @Override
    @GetMapping("/create/fetchgame")
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
                    //한글 요일 변환 후 DTO 생성
                    GameInfoResponseDTO responseDTO = GameInfoResponseDTO.fromEntity(info);
                    responseDTO.setDay(DateUtils.getKoreanDay(gameDate)); // 한글 요일 설정
                    return ResponseEntity.ok(responseDTO);
                })
                .orElse(ResponseEntity.status(404).build());  // 404 처리 (No matching game found)
    }

    //일기 저장
    @PostMapping("/create")
    public ResponseEntity<?> createDiary(@RequestBody DiaryAddRequestDTO request, HttpServletRequest req) {
        // 현재 로그인한 사용자 조회
        Long memberId = accountHelper.getMemberId(req);
        if (memberId == null) {
            return ResponseEntity.status(401).body("{\"status\": 401, \"message\": \"로그인이 필요합니다.\"}");
        }

        Optional<User> userOptional = userRepository.findById(memberId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body("{\"status\": 404, \"message\": \"사용자를 찾을 수 없습니다.\"}");
        }

        User user = userOptional.get();

        // gameId를 이용해 GameInfo 조회
        Optional<GameInfo> gameInfoOpt = gameInfoRepository.findById(request.getGameId());
        if (gameInfoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("{\"status\": 400, \"message\": \"해당 gameId에 대한 경기 정보가 존재하지 않습니다.\"}");
        }

        GameInfo gameInfo = gameInfoOpt.get();

        // 기존 Diary가 있는지 확인
        if (diaryRepository.existsByGameInfo(gameInfo)) {
            return ResponseEntity.status(400).body("{\"status\": 400, \"message\": \"해당 gameId에 대한 일기가 이미 존재합니다.\"}");
        }

        // 필수값 검증
        if (request.getContents() == null || request.getViewType() == null || request.getScore() == null) {
            return ResponseEntity.badRequest().body("{\"status\": 400, \"message\": \"contents, viewType, score는 필수 입력 값입니다.\"}");
        }

        //ViewType 변환 코드 제거 (이미 ViewType이면 변환 불필요)
        ViewType viewType = request.getViewType();

        //경기 날짜와 요일 가져오기
        LocalDate gameDate = gameInfo.getGameDate();
        String gameDay = DateUtils.getKoreanDay(gameDate);

        // 새로운 Diary 생성
        Diary diary = new Diary();
        diary.setGameInfo(gameInfo);
        diary.setDate(gameDate);
        diary.setDay(gameDay);
        diary.setContents(request.getContents());
        diary.setViewType(viewType); //변환 없이 그대로 저장
        diary.setScore(request.getScore());
        diary.setUser(user);
        diary.setImgUrls((request.getImgUrls() == null || request.getImgUrls().isEmpty()) ? null : request.getImgUrls());

        // Diary 저장
        diaryRepository.save(diary);

        return ResponseEntity.ok("{\"status\": 200, \"message\": \"일기를 성공적으로 저장했습니다.\"}");
    }



    // 삭제 기능 추가
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> deleteDiary(@PathVariable("diaryId") Long diaryId, HttpServletRequest req) {
        // 현재 로그인한 사용자 조회
        Long memberId = accountHelper.getMemberId(req);
        if (memberId == null) {
            return ResponseEntity.status(401).body("{\"status\": 401, \"message\": \"로그인이 필요합니다.\"}");
        }

        Optional<User> userOptional = userRepository.findById(memberId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body("{\"status\": 404, \"message\": \"사용자를 찾을 수 없습니다.\"}");
        }

        User user = userOptional.get();

        // diaryId로 Diary 조회
        Optional<Diary> diaryOpt = diaryRepository.findById(diaryId);
        if (diaryOpt.isEmpty()) {
            return ResponseEntity.status(404).body("{\"status\": 404, \"message\": \"해당 diaryId에 대한 일기가 존재하지 않습니다.\"}");
        }

        Diary diary = diaryOpt.get();

        // 현재 로그인한 사용자가 해당 일기의 작성자인지 확인
        if (!diary.getUser().getCertificateId().equals(user.getCertificateId())) {
            return ResponseEntity.status(403).body("{\"status\": 403, \"message\": \"삭제 권한이 없습니다.\"}");
        }

        // Diary 삭제
        diaryRepository.delete(diary);

        return ResponseEntity.ok("{\"status\": 200, \"message\": \"일기가 성공적으로 삭제되었습니다.\"}");
    }



}
