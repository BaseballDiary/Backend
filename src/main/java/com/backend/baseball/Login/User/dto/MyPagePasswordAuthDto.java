package com.backend.baseball.Login.User.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyPagePasswordAuthDto {
    @NotBlank(message="현재 비밀번호를 입력해야 합니다.")
    private String currentPassword;

    @Builder
    public MyPagePasswordAuthDto(String currentPassword) {
        this.currentPassword = currentPassword;
    }

}
