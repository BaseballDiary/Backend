package com.backend.baseball.Login.User.emailauth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MailCertificationDto {
    @NotNull @Email
    private String email;
    @NotNull
    private String authNumber;

    @Builder
    public MailCertificationDto(String email, String authNumber) {
        this.email = email;

    }
}
