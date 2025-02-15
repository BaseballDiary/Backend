package com.backend.baseball.Diary.controller;

import com.backend.baseball.Diary.dto.DiaryResponseDTO;
import com.backend.baseball.Diary.dto.DiaryUpdateRequestDTO;
import com.backend.baseball.Diary.dto.SaveDiaryRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface AddDiaryControllerDocs {

    @Operation(summary = "일기 저장", description = "경기 ID를 기반으로 내용을 추가하거나 업데이트하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일기 저장 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DiaryResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (gameId가 없음)",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"message\": \"gameId가 필요합니다.\" }"))
            ),
            @ApiResponse(responseCode = "404", description = "해당 gameId의 일기가 존재하지 않음",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"message\": \"해당 gameId의 일기를 찾을 수 없습니다.\" }"))
            ),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"message\": \"로그인이 필요합니다.\" }"))
            )
    })
    ResponseEntity<DiaryResponseDTO> saveDiary(@RequestBody SaveDiaryRequestDTO request, HttpSession session);


    @Operation(summary = "일기 수정", description = "특정 다이어리 ID에 대한 내용 업데이트 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일기 수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DiaryResponseDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "해당 ID의 일기를 찾을 수 없음",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"message\": \"해당 ID의 일기를 찾을 수 없습니다.\" }"))
            ),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"message\": \"로그인이 필요합니다.\" }"))
            )
    })
    ResponseEntity<DiaryResponseDTO> updateDiary(@PathVariable Long diaryId, @RequestBody DiaryUpdateRequestDTO request, HttpSession session);


    @Operation(summary = "일기 삭제", description = "특정 다이어리 ID를 삭제하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일기 삭제 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"status\": 200, \"message\": \"일기를 성공적으로 삭제했습니다.\" }"))
            ),
            @ApiResponse(responseCode = "404", description = "해당 ID의 일기를 찾을 수 없음",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"message\": \"해당 ID의 일기를 찾을 수 없습니다.\" }"))
            ),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"message\": \"로그인이 필요합니다.\" }"))
            )
    })
    ResponseEntity<Map<String, Object>> deleteDiary(@PathVariable Long diaryId, HttpSession session);
}
