package com.backend.baseball.Login.User.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPasswordChangeDto {
    @NotNull
    private String currentPassword;

    @NotNull
    private String newPassword;

    @NotNull
    private String newPasswordConfirm;

    @Builder
    public UserPasswordChangeDto(String currentPassword, String newPassword, String newPasswordConfirm) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.newPasswordConfirm = newPasswordConfirm;
    }

}
