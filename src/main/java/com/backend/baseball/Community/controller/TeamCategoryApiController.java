package com.backend.baseball.Community.controller;

import com.backend.baseball.Community.entity.TeamCategory;
import com.backend.baseball.Community.service.TeamCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TeamCategoryApiController {
    private final TeamCategoryService teamCategoryService;
    /*
    @PostMapping("/selectTeamClub")
    public ResponseEntity<String> selectTeamClub(@RequestParam String teamCategoryTitle) {
        if("KBO".equalsIgnoreCase(teamCategoryTitle)) {
            return ResponseEntity.ok("/community/all");
        }
        return ResponseEntity.ok("/community/"+teamCategoryTitle);
    }
    */
}
