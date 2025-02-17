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

    @Operation(summary = "경기 정보 우선 저장", description = "가져온 경기 정보 diary 테이블에 우선 저장")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diary entry created", content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - login required"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    @PostMapping("/create/saveGame")
    public abstract ResponseEntity<?> saveGame(@RequestBody SaveGameRequestDTO saveGameRequest, HttpServletRequest req);

    @Operation(summary = "야구 일기 최종 생성", description = "일기 내용과 img, viewType 받아서 최종 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diary entry updated", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "No matching diary entry found")
    })
    @PostMapping("/create")
    public abstract ResponseEntity<?> createOrUpdateDiary(@RequestBody DiaryAddRequestDTO request);
}
