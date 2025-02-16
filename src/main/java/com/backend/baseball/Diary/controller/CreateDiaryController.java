package com.backend.baseball.Diary.controller;

import com.backend.baseball.Diary.dto.CreateDiary.GameInfoResponseDTO;
import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.GameInfo.repository.GameInfoRepository;
import com.backend.baseball.User.entity.User;
import com.backend.baseball.User.helper.AccountHelper;
import com.backend.baseball.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;


import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/diary")
public class CreateDiaryController {

    private final GameInfoRepository gameInfoRepository;
    private final AccountHelper accountHelper;
    private final UserRepository userRepository;

    @PostMapping("/create/fetchgame")
    public ResponseEntity<?> fetchGame(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateString, HttpServletRequest req) {
        // 1️⃣ String -> LocalDate 변환
        LocalDate gameDate;
        try {
            gameDate = LocalDate.parse(dateString);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid date format. Use YYYY-MM-DD.");
        }

        // 2️⃣ 현재 로그인한 사용자의 myClub 조회
        Long memberId = accountHelper.getMemberId(req);
        if (memberId == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        // 3️⃣ 사용자의 myClub 가져오기
        Optional<User> userOptional = userRepository.findById(memberId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body("사용자를 찾을 수 없습니다.");
        }

        String myClub = userOptional.get().getMyClub();

        // 4️⃣ 해당 날짜와 myClub이 포함된 경기 조회
        Optional<GameInfo> gameInfo = gameInfoRepository.findByGameDateAndTeam(gameDate, myClub);

        return gameInfo
                .map(info -> ResponseEntity.ok(GameInfoResponseDTO.fromEntity(info)))
                .orElse(ResponseEntity.status(404).build());  // 404 처리 (No matching game found)
    }

}
