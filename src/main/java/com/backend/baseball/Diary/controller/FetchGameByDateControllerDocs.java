package com.backend.baseball.Diary.controller;

import com.backend.baseball.Diary.dto.DiaryResponseDTO;
import com.backend.baseball.Diary.dto.FetchGameByDateDTO;
import com.backend.baseball.Diary.dto.GameResponseDTO;
import com.backend.baseball.Diary.dto.viewDiary.MyClubResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface FetchGameByDateControllerDocs {

    @Operation(summary = "내 구단 정보 반환", description = "로그인한 사용자의 구단 정보를 반환하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 구단 반환 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MyClubResponseDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"message\": \"로그인이 필요합니다.\" }")))
    })
    ResponseEntity<MyClubResponseDTO> fetchMyClub(HttpSession session);

    @Operation(summary = "사용자 팀 경기 일정 조회", description = "특정 날짜의 사용자 팀이 포함된 경기 일정을 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경기 일정 반환 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameResponseDTO.class),examples = @ExampleObject(value = """
                            {
                                "gameId": 123,
                                "team1": "두산",
                                "team2": "LG",
                                "team1Score": 5,
                                "team2Score": 3,
                                "gameDate": "2025-02-15",
                                "day": "토요일",
                                "time": "18:00",
                                "location": "잠실구장",
                                "gameStatus": "종료"
                            }
                            """))
            ),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"message\": \"로그인이 필요합니다.\" }"))
            ),
            @ApiResponse(responseCode = "404", description = "해당 날짜에 경기 정보가 없습니다.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"message\": \"해당 날짜에 경기 정보가 없습니다.\" }"))
            )
    })
    ResponseEntity<GameResponseDTO> fetchUserTeamGameByDate(@RequestBody FetchGameByDateDTO request, HttpSession session);

    @Operation(summary = "경기 정보를 조회하고 일기 저장", description = "특정 날짜와 사용자 구단 정보를 이용하여 경기 정보를 조회한 후, 해당 경기로 일기를 저장하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일기 저장 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DiaryResponseDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"message\": \"로그인이 필요합니다.\" }"))
            ),
            @ApiResponse(responseCode = "404", description = "해당 날짜의 경기 정보를 찾을 수 없습니다.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"message\": \"해당 날짜의 경기 정보를 찾을 수 없습니다.\" }"))
            ),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    ResponseEntity<DiaryResponseDTO> saveGameDiary(@RequestBody GameResponseDTO request, HttpSession session);
}