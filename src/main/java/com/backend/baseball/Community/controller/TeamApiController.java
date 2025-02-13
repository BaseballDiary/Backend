package com.backend.baseball.Community.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/teams") // 공통 URL 적용
public class TeamApiController {

    /** 📌 모든 구단 목록 반환 */
    @GetMapping
    public ResponseEntity<List<String>> getTeams() {
        List<String> teams = Arrays.asList("SSG", "LG", "KIA", "두산", "한화", "KT", "NC", "롯데", "삼성", "키움");
        return ResponseEntity.ok(teams);
    }
}
