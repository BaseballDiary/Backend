package com.backend.baseball.GameInfo.controller;

import com.backend.baseball.GameInfo.apiPayload.ApiResponse;
import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.GameInfo.service.GameInfoService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
@Slf4j
public class GameInfoController implements GameInfoControllerDocs{

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
    public ResponseEntity<ApiResponse<GameInfo>> myClubGameInfo(@RequestParam String date, HttpSession session) {
        log.info("내 구단 경기 조회 컨트롤러 실행");
        try {
            String username = (String) session.getAttribute("username"); // 세션에서 사용자 가져오기

            if (username == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("인증된 사용자가 아닙니다.", null));
            }

            GameInfo myClubGameInfo = gameInfoService.getMyClubGameInfo(username, date);
            if(myClubGameInfo==null)
                return ResponseEntity.status(400).body(new ApiResponse<>("API 실패: 내 구단 경기 순위 조회 중 오류 발생", null));

            return ResponseEntity.ok(new ApiResponse("내 구단 경기 순위 조회 성공", myClubGameInfo));// 경기 없음 & 경기 정보 구분???
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse<>("서버 오류: " + e.getMessage(), null));
        }
    }
}
