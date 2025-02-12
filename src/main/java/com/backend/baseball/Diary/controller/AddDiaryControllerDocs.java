package com.backend.baseball.Diary.controller;

import com.backend.baseball.Diary.dto.DiaryResponseDTO;
import com.backend.baseball.Diary.dto.DiaryUpdateRequestDTO;
import com.backend.baseball.Diary.dto.SaveDiaryRequestDTO;
import com.backend.baseball.Diary.dto.viewDiary.DiaryDetailDTO;
import com.backend.baseball.Diary.dto.viewDiary.StatsResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface AddDiaryControllerDocs {
    @Operation(summary = "일기 저장", description = "야구 일기를 저장하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일기 저장 성공"),
            @ApiResponse(responseCode = "400", description = "일기 저장 실패")})
    ResponseEntity<DiaryResponseDTO> saveDiary(SaveDiaryRequestDTO request, HttpSession session);

    @Operation(summary = "일기 수정", description = "야구 일기를 수정하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일기 수정 성공"),
            @ApiResponse(responseCode = "400", description = "일기 수정 실패")})
    ResponseEntity<DiaryResponseDTO> updateDiary(@PathVariable Long diaryId, DiaryUpdateRequestDTO request, HttpSession session);

    @Operation(summary = "일기 삭제", description = "야구 일기를 삭제하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일기 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "일기 삭제 실패")})
    ResponseEntity<Map<String, Object>> deleteDiary(@PathVariable Long diaryId, HttpSession session);

}
