package com.backend.baseball.GameInfo.controller;

import com.backend.baseball.GameInfo.entity.GameInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GameInfoControllerDocs {

    @Operation(summary = "오늘의 경기 반환", description = "오늘의 경기 정보를 반환하는 API 이며, query String으로 date 값을 주세요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "오늘의 경기 반환 성공"),
            @ApiResponse(responseCode = "400", description = "오늘의 경기 반환 실패") })
    ResponseEntity<com.backend.baseball.GameInfo.apiPayload.ApiResponse<List<GameInfo>>> todayGameInfo(@RequestParam String date);

    @Operation(summary = "내 구단 경기 반환", description = "내 구단 경기 정보를 반환하는 API 이며, query String으로 date 값을 주세요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 구단 경기 반환 성공"),
            @ApiResponse(responseCode = "400", description = "내 구단 경기 반환 실패") })
    ResponseEntity<com.backend.baseball.GameInfo.apiPayload.ApiResponse<GameInfo>> myClubGameInfo(@RequestParam String date, HttpSession session);
}
