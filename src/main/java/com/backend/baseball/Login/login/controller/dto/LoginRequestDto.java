package com.backend.baseball.Login.login.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    @NotBlank(message = "이메일은 필수사항입니다.")
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String email;
    @NotBlank(message = "비밀번호는 필수사항입니다.")
    private String password;
}
