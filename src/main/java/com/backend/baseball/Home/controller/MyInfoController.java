package com.backend.baseball.Home.controller;

import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.GameInfo.repository.GameInfoRepository;
import com.backend.baseball.Home.dto.MyInfoResponseDTO;
import com.backend.baseball.User.entity.User;
import com.backend.baseball.User.helper.AccountHelper;
import com.backend.baseball.User.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/start")
public class MyInfoController {
    private final AccountHelper accountHelper;
    private final UserRepository userRepository;
    private final GameInfoRepository gameInfoRepository;


    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getUserInfoAndTodayGame(HttpServletRequest req) {
        // 현재 로그인된 사용자 ID 가져오기
        Long memberId = accountHelper.getMemberId(req);
        if (memberId == null) {
            return ResponseEntity.status(401).body(Map.of("status", 401, "message", "로그인이 필요합니다."));
        }

        // 사용자 정보 조회
        Optional<User> userOptional = userRepository.findById(memberId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("status", 404, "message", "사용자를 찾을 수 없습니다."));
        }

        User user = userOptional.get();
        String nickname = user.getNickname();
        String myClub = user.getMyClub();

        // 오늘 날짜 기준 경기 정보 조회
        LocalDate today = LocalDate.now();
        Optional<GameInfo> gameInfoOptional = gameInfoRepository.findByGameDateAndTeam(today, myClub);

        // 응답 JSON 구성
        Map<String, Object> response = new HashMap<>();
        response.put("nickname", nickname);

        if (gameInfoOptional.isPresent()) {
            response.put("gameInfo", MyInfoResponseDTO.fromEntity(gameInfoOptional.get()));
        } else {
            response.put("gameInfo", "오늘의 경기가 없습니다.");
        }

        return ResponseEntity.ok(response);
    }
}
