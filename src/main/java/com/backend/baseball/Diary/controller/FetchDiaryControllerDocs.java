package com.backend.baseball.Diary.controller;

import com.backend.baseball.Diary.dto.viewDiary.DiaryDetailDTO;
import com.backend.baseball.Diary.dto.viewDiary.StatsResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface FetchDiaryControllerDocs {

    @Operation(summary = "년도별 경기 스탯 반환", description = "특정 년도의 팀 및 개인 경기 스탯을 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경기 스탯 조회 성공"),
            @ApiResponse(responseCode = "400", description = "경기 스탯 조회 실패")})
    ResponseEntity<StatsResponseDTO> getStats(@PathVariable String year, HttpSession session);

    @Operation(summary = "직관/집관 일기 조회", description = "특정 년도의 직관/집관 일기 리스트를 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일기 조회 성공"),
            @ApiResponse(responseCode = "400", description = "일기 조회 실패")})
    ResponseEntity<?> getDiaries(@PathVariable String year, @RequestParam String status, HttpSession session);

    @Operation(summary = "특정 일기 조회", description = "diaryId를 통해 특정 일기의 상세 정보를 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일기 조회 성공"),
            @ApiResponse(responseCode = "400", description = "일기 조회 실패")})
    ResponseEntity<DiaryDetailDTO> getDiary(@PathVariable Long diaryId);

}
