package com.backend.baseball.Login.User.emailauth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MailAddressDto {
    @NotNull @Email
    private String email;

    @Builder
    public MailAddressDto(String email) {
        this.email = email;
    }

}
