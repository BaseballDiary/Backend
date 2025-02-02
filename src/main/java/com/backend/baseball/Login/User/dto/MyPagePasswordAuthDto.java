package com.backend.baseball.Login.User.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyPagePasswordAuthDto {
    @NotNull
    private String currentPassword;

    @Builder
    public MyPagePasswordAuthDto(String currentPassword) {
        this.currentPassword = currentPassword;
    }

}
