package com.backend.baseball.Diary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface FetchDiaryDocs {

    @Operation(summary = "연도별 일기 조회", description = "특정 연도의 모든 일기를 조회하는 API. Path Variable로 year 값을 입력하세요.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "연도별 일기 조회 성공"),
            @ApiResponse(responseCode = "400", description = "연도별 일기 조회 실패") })
    ResponseEntity<DiaryResponse> getDiariesByYear(@PathVariable("year") int year, HttpSession session);
}
