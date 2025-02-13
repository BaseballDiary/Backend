package com.backend.baseball.Community.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/teams") // 공통 URL 적용
public class TeamApiController {


    @PostMapping
    public ResponseEntity<String> selectTeam(@RequestParam String teamClub) {
        List<String> teams = Arrays.asList("SSG", "LG", "KIA", "두산", "한화", "KT", "NC", "롯데", "삼성", "키움");

        if (!teams.contains(teamClub)) {
            return ResponseEntity.badRequest().body("존재하지 않는 구단입니다.");
        }
        return ResponseEntity.ok("구단이 성공적으로 선택되었습니다: " + teamClub);
    }

}
