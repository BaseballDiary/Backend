package com.backend.baseball.User.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyClubResponseDTO {
    @Schema(description = "사용자의 구단 이름", example = "두산")
    private String myClub;
}

