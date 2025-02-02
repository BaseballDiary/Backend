package com.backend.baseball.Diary.controller;

import com.backend.baseball.Diary.dto.AddDiaryRequest;
import com.backend.baseball.Diary.dto.UpdateDiaryRequest;
import com.backend.baseball.Diary.entity.Diary;
import com.backend.baseball.GameInfo.entity.GameInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "일기 관리", description = "일기 CRUD 및 관련 기능 API") // ✅ 그룹 이름 추가
public interface AddDiaryDocs {

    @Operation(summary = "게임 정보 조회", description = "유저 클럽과 날짜로 게임 정보를 조회하는 API. Query String으로 date 값을 주세요.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게임 정보 반환 성공"),
            @ApiResponse(responseCode = "400", description = "게임 정보 반환 실패") })
    ResponseEntity<?> fetchGame(@RequestParam("date") String date, HttpSession session);

    @Operation(summary = "일기 생성", description = "새로운 일기를 생성하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "일기 생성 성공"),
            @ApiResponse(responseCode = "400", description = "일기 생성 실패") })
    ResponseEntity<Diary> addDiary(@RequestBody AddDiaryRequest request, HttpSession session);

    @Operation(summary = "일기 수정", description = "기존 일기의 내용을 수정하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일기 수정 성공"),
            @ApiResponse(responseCode = "400", description = "일기 수정 실패") })
    ResponseEntity<Diary> updateDiary(@PathVariable Long diaryId, @RequestBody UpdateDiaryRequest request, HttpSession session);

    @Operation(summary = "일기 삭제", description = "기존 일기를 삭제하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "일기 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "일기 삭제 실패") })
    ResponseEntity<Void> deleteDiary(@PathVariable Long diaryId, HttpSession session);
}
