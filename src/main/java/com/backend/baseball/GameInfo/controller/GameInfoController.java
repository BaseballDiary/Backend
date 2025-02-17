package com.backend.baseball.GameInfo.controller;

import com.backend.baseball.GameInfo.apiPayload.ApiResponse;
import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.GameInfo.service.GameInfoService;
import com.backend.baseball.User.entity.User;
import com.backend.baseball.User.helper.AccountHelper;
import com.backend.baseball.User.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
@Slf4j
public class GameInfoController implements GameInfoControllerDocs{

    private final AccountHelper accountHelper;
    private final UserRepository userRepository;
    private final GameInfoService gameInfoService;


    @Override
    @GetMapping("/today")
    public ResponseEntity<ApiResponse<List<GameInfo>>> todayGameInfo(@RequestParam String date) {
        log.info("오늘의 경기 조회 컨트롤러 실행");
        try {
            List<GameInfo> list = gameInfoService.getTodayGameInfo(date);
            if(list==null)
                return ResponseEntity.ok(new ApiResponse("오늘의 경기가 없습니다.", null));
            if(list!=null && list.isEmpty())
                return ResponseEntity.status(400).body(new ApiResponse<>("API 실패: 오늘의 경기 순위 조회 중 오류 발생", null));

            return ResponseEntity.ok(new ApiResponse("오늘의 경기 순위 조회 성공", list));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse<>("서버 오류: " + e.getMessage(), null));
        }
    }

    @Override
    @GetMapping("/myClub")
    public ResponseEntity<ApiResponse<GameInfo>> myClubGameInfo(@RequestParam String date, HttpServletRequest req) {
        log.info("내 구단 경기 조회 컨트롤러 실행");
        try {
            Long memberId = accountHelper.getMemberId(req);
            // 로그인되지 않은 경우
            if (memberId == null) {
                return ResponseEntity.status(401).body(new ApiResponse<>("로그인이 필요합니다.", null));
            }

            // 사용자 정보 조회
            Optional<User> userOptional = userRepository.findById(memberId.longValue());

            // 사용자 정보가 없을 경우
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(404).body(new ApiResponse<>("사용자를 찾을 수 없습니다.", null));
            }

            User user = userOptional.get();
            GameInfo myClubGameInfo = gameInfoService.getMyClubGameInfo(user.getEmail(), date);
            if(myClubGameInfo==null)
                return ResponseEntity.status(400).body(new ApiResponse<>("API 실패: 내 구단 경기 순위 조회 중 오류 발생", null));

            return ResponseEntity.ok(new ApiResponse("내 구단 경기 순위 조회 성공", myClubGameInfo));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse<>("서버 오류: " + e.getMessage(), null));
        }
    }
}