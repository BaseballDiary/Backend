package com.backend.baseball.User.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {
    private String email;    // 로그인한 유저의 이메일
    private String message;  // 응답 메시지
    private String sessionId; // 세션 ID 추가 ✅
}