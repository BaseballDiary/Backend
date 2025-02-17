package com.backend.baseball.Diary.controller;

import com.backend.baseball.Diary.dto.CreateDiary.DiaryAddRequestDTO;
import com.backend.baseball.Diary.dto.CreateDiary.GameInfoResponseDTO;
import com.backend.baseball.Diary.dto.CreateDiary.SaveGameRequestDTO;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Diary Create API", description = "야구 일기 생성 API")
public abstract class CreateDiaryControllerDocs {

    @Operation(summary = "날짜로 경기 정보 받아오기", description = "날짜로 경기 정보 받아오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game found", content = @Content(schema = @Schema(implementation = GameInfoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No matching game found")
    })
    @PostMapping("/create/fetchgame")
    public abstract ResponseEntity<?> fetchGame(@RequestParam("date") String dateString, HttpServletRequest req);


    @Operation(summary = "야구 일기 생성", description = "사용자의 certificateId를 가져와 일기를 생성함")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일기 생성 성공", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "필수 값 누락 or 일기 중복 생성"),
            @ApiResponse(responseCode = "401", description = "로그인 필요"),
            @ApiResponse(responseCode = "404", description = "사용자 정보 없음 or 경기 정보 없음")
    })
    @PostMapping("/create")
    public abstract ResponseEntity<?> createDiary(@RequestBody DiaryAddRequestDTO request, HttpServletRequest req);
}
