package com.backend.baseball.Diary.controller;

import com.backend.baseball.Diary.dto.FetchGameByDateDTO;
import com.backend.baseball.Diary.dto.GameResponseDTO;
import com.backend.baseball.GameInfo.service.GameInfoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
//프론트에서 년-월-일 보내면 해당하는 경기 정보 보내주기
public class FetchGameByDateController {

    private final GameInfoService gameInfoService;


    //로그인한 사용자의 `certificated_id`를 통해 내 구단이 포함된 경기 일정만 조회
    @PostMapping("/create/fetchgame")
    public List<GameResponseDTO> fetchUserTeamGamesByDate(
            @RequestBody FetchGameByDateDTO request,
            HttpSession session) {

        //1. 세션에서 로그인한 사용자의 `certificated_id` 가져오기
        Long certificatedId = (Long) session.getAttribute("certificated_id");

        if (certificatedId == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        //2. 특정 날짜의 경기 중, 사용자의 구단이 포함된 경기만 필터링하여 반환
        return gameInfoService.getUserTeamGameInfoByDate(request.getGameDate().toString(), certificatedId);
    }
}