package com.backend.baseball.Login.User.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Not;

@Data
@NoArgsConstructor
public class UserPasswordResetDto {
    @NotNull @Email
    private String email;
    @NotNull
    private String newPassword;
    @NotNull
    private String newPasswordConfirm;

    @Builder
    public UserPasswordResetDto(String email, String newPassword, String newPasswordConfirm) {
        this.email = email;
        this.newPassword = newPassword;
        this.newPasswordConfirm = newPasswordConfirm;
    }

}
