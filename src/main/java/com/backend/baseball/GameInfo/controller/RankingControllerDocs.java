package com.backend.baseball.GameInfo.controller;

import com.backend.baseball.GameInfo.entity.PitcherRanking;
import com.backend.baseball.GameInfo.entity.PitcherRecordRanking;
import com.backend.baseball.GameInfo.entity.TeamRanking;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RankingControllerDocs {
    @Operation(summary = "팀 순위 반환", description = "전체 팀 순위 정보를 반환하는 API 이며, query String으로 year 값을 주세요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팀 순위 반환 성공"),
            @ApiResponse(responseCode = "400", description = "팀 순위 반환 실패") })
    ResponseEntity<com.backend.baseball.GameInfo.apiPayload.ApiResponse<List<TeamRanking>>> teamRanking(@RequestParam String year);

    @Operation(summary = "투수 개인 순위 반환", description = "투수 개인 순위 정보를 반환하는 API 이며, query String으로 year 값을 주세요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "투수 개인 순위 반환 성공"),
            @ApiResponse(responseCode = "400", description = "투수 개인 순위 반환 실패") })
    ResponseEntity<com.backend.baseball.GameInfo.apiPayload.ApiResponse<List<PitcherRanking>>> pitcherRanking(@RequestParam String year);

    @Operation(summary = "투수 기록 순위 반환", description = "투수 기록 순위 정보를 반환하는 API 이며, query String으로 year 값을 주세요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "투수 기록 순위 반환 성공"),
            @ApiResponse(responseCode = "400", description = "투수 기록 순위 반환 실패") })
    ResponseEntity<com.backend.baseball.GameInfo.apiPayload.ApiResponse<List<PitcherRecordRanking>>> pitcherRecordRanking(@RequestParam String year);

    @Operation(summary = "타자 개인 순위 반환", description = "타자 개인 순위 정보를 반환하는 API 이며, query String으로 year 값을 주세요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "타자 개인 순위 반환 성공"),
            @ApiResponse(responseCode = "400", description = "타자 개인 순위 반환 실패") })
    ResponseEntity<com.backend.baseball.GameInfo.apiPayload.ApiResponse<List<PitcherRanking>>> batterRanking(@RequestParam String year);

    @Operation(summary = "타자 기록 순위 반환", description = "타자 기록 순위 정보를 반환하는 API 이며, query String으로 year 값을 주세요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "타자 기록 순위 반환 성공"),
            @ApiResponse(responseCode = "400", description = "타자 기록 순위 반환 실패") })
    ResponseEntity<com.backend.baseball.GameInfo.apiPayload.ApiResponse<List<PitcherRecordRanking>>> batterRecordRanking(@RequestParam String year);
}
