package com.backend.baseball.GameInfo.controller;

import com.backend.baseball.GameInfo.entity.TeamRanking;
import com.backend.baseball.GameInfo.service.TeamRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class TeamRankingController {
    private final TeamRankingService teamRankingService;

    @GetMapping("/ranking/teams")
    public ResponseEntity<List<TeamRanking>> teamRanking (@RequestParam String year){
        List<TeamRanking> list = teamRankingService.getTeamRanking(year);
        return ResponseEntity.ok(list);
    }
}
