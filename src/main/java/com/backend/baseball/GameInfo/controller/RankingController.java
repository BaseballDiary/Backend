package com.backend.baseball.GameInfo.controller;

import com.backend.baseball.GameInfo.apiPayload.ApiResponse;
import com.backend.baseball.GameInfo.entity.*;
import com.backend.baseball.GameInfo.service.RankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class RankingController implements RankingControllerDocs{
    private final RankingService rankingService;

    @Override
    @GetMapping("/ranking/teams")
    public ResponseEntity<ApiResponse<List<TeamRanking>>> teamRanking(@RequestParam String year) {
        log.info("팀 순위 조회 컨트롤러 실행");
        try {
            List<TeamRanking> list = rankingService.getTeamRanking(year);
            if(list.isEmpty())
                return ResponseEntity.status(400).body(new ApiResponse<>("API 실패: 팀 순위 조회 중 오류 발생", null));

            return ResponseEntity.ok(new ApiResponse("팀 순위 조회 성공", list));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse<>("서버 오류: " + e.getMessage(), null));
        }
    }

    @Override
    @GetMapping("/ranking/pitchers")
    public ResponseEntity<ApiResponse<List<PitcherRanking>>> pitcherRanking(@RequestParam String year) {
        log.info("투수 개인 순위 조회 컨트롤러 실행");
        try {
            List<PitcherRanking> list = rankingService.getPitcherRanking(year);
            if(list.isEmpty())
                return ResponseEntity.status(400).body(new ApiResponse<>("API 실패: 투수 개인 순위 조회 중 오류 발생", null));

            return ResponseEntity.ok(new ApiResponse("투수 개인 순위 조회 성공", list));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse<>("서버 오류: " + e.getMessage(), null));
        }
    }

    @Override
    @GetMapping("/ranking/pitchers/record")
    public ResponseEntity<ApiResponse<List<PitcherRecordRanking>>> pitcherRecordRanking(String year) {
        log.info("투수 기록 순위 조회 컨트롤러 실행");
        try {
            List<PitcherRecordRanking> list = rankingService.getPitcherRecordRanking(year);
            if(list.isEmpty())
                return ResponseEntity.status(400).body(new ApiResponse<>("API 실패: 투수 기록 순위 조회 중 오류 발생", null));

            return ResponseEntity.ok(new ApiResponse("투수 기록 순위 조회 성공", list));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse<>("서버 오류: " + e.getMessage(), null));
        }
    }

    @Override
    @GetMapping("/ranking/batters")
    public ResponseEntity<ApiResponse<List<PitcherRanking>>> batterRanking(String year) {
        log.info("타자 개인 순위 조회 컨트롤러 실행");
        try {
            List<BatterRanking> list = rankingService.getBatterRanking(year);
            if(list.isEmpty())
                return ResponseEntity.status(400).body(new ApiResponse<>("API 실패: 타자 개인 순위 조회 중 오류 발생", null));

            return ResponseEntity.ok(new ApiResponse("타자 개인 순위 조회 성공", list));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse<>("서버 오류: " + e.getMessage(), null));
        }
    }

    @Override
    @GetMapping("ranking/batters/record")
    public ResponseEntity<ApiResponse<List<PitcherRecordRanking>>> batterRecordRanking(String year) {
        log.info("타자 기록 순위 조회 컨트롤러 실행");
        try {
            List<BatterRecordRanking> list = rankingService.getBatterRecordRanking(year);
            if(list.isEmpty())
                return ResponseEntity.status(400).body(new ApiResponse<>("API 실패: 타자 기록 순위 조회 중 오류 발생", null));

            return ResponseEntity.ok(new ApiResponse("타자 기록 순위 조회 성공", list));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse<>("서버 오류: " + e.getMessage(), null));
        }
    }

}
