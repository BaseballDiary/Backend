package com.backend.baseball.Login.User.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPasswordChangeDto {

    @Schema(description = "현재 비밀번호", example = "oldpassword123")
    @NotBlank(message = "현재 비밀번호를 입력해야 합니다.")
    private String currentPassword;

    @Schema(description = "새 비밀번호", example = "newpassword456")
    @NotBlank(message = "새 비밀번호를 입력해야 합니다.")
    @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다.")
    private String newPassword;

    @Schema(description = "새 비밀번호 확인", example = "newpassword456")
    @NotBlank(message = "새 비밀번호 확인을 입력해야 합니다.")
    private String newPasswordConfirm;

    @Builder
    public UserPasswordChangeDto(String currentPassword, String newPassword, String newPasswordConfirm) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.newPasswordConfirm = newPasswordConfirm;
    }
}
