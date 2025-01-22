package com.backend.baseball.GameInfo.controller;

import com.backend.baseball.GameInfo.apiPayload.ApiResponse;
import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.GameInfo.service.GameInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<ApiResponse<List<GameInfo>>> todayGameInfo(String date) {
        log.info("오늘의 경기 조회 컨트롤러 실행");
        try {
            List<GameInfo> list = gameInfoService.getTodayGameInfo(date);
            return ResponseEntity.ok(new ApiResponse("오늘의 경기 순위 조회 성공", list));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(new ApiResponse<>("API 실패: 오늘의 경기 순위 조회 중 오류 발생", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<GameInfo>>> myClubGameInfo(String date) {
        return null;
    }

    /*@Override
    @GetMapping("/myClub")
    public ResponseEntity<ApiResponse<List<GameInfo>>> myClubGameInfo(String date) {
        log.info("내 구단 경기 조회 컨트롤러 실행");
        try {
            List<GameInfo> list = gameInfoService.(date);
            return ResponseEntity.ok(new ApiResponse("내 구단 경기 순위 조회 성공", list));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(new ApiResponse<>("API 실패: 내 구단 경기 순위 조회 중 오류 발생", null));
        }
    }*/
}
