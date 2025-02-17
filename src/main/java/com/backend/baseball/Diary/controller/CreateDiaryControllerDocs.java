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

@Tag(name = "Diary Create API", description = "API for managing baseball diaries")
public abstract class CreateDiaryControllerDocs {

    @Operation(summary = "Fetch game by date", description = "Finds a game by the provided date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game found", content = @Content(schema = @Schema(implementation = GameInfoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No matching game found")
    })
    @PostMapping("/create/fetchgame")
    public abstract ResponseEntity<?> fetchGame(@RequestParam("date") String dateString, HttpServletRequest req);

    @Operation(summary = "Save game to diary", description = "Saves a game entry to the diary.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diary entry created", content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - login required"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    @PostMapping("/create/saveGame")
    public abstract ResponseEntity<?> saveGame(@RequestBody SaveGameRequestDTO saveGameRequest, HttpServletRequest req);

    @Operation(summary = "Create or update diary entry", description = "Updates an existing diary entry or creates a new one.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diary entry updated", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "No matching diary entry found")
    })
    @PostMapping("/create")
    public abstract ResponseEntity<?> createOrUpdateDiary(@RequestBody DiaryAddRequestDTO request);
}
