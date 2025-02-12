package com.backend.baseball.Diary.controller;

import com.backend.baseball.Diary.dto.DiaryResponseDTO;
import com.backend.baseball.Diary.dto.FetchGameByDateDTO;
import com.backend.baseball.Diary.dto.GameResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface FetchGameByDateControllerDocs {
    @Operation(summary = "특정 날짜의 경기 조회", description = "프론트에서 년-월-일을 보내면 해당하는 경기 정보를 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경기 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "경기 정보 조회 실패")})
    ResponseEntity<List<GameResponseDTO>> fetchUserTeamGamesByDate(@RequestBody FetchGameByDateDTO request, HttpSession session);

    @Operation(summary = "경기 조회 후 다이어리 저장", description = "특정 날짜와 팀 정보를 기반으로 경기 정보를 조회 후 다이어리를 저장")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경기 조회 및 다이어리 저장 성공"),
            @ApiResponse(responseCode = "400", description = "경기 조회 실패 또는 저장 오류")})
    ResponseEntity<DiaryResponseDTO> fetchGameAndSaveDiary(@RequestBody FetchGameByDateDTO request, HttpSession session);

}
