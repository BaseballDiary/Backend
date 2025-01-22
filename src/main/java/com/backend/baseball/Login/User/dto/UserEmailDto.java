package com.backend.baseball.Login.User.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserEmailDto {
    @Email
    private String email;

    @Builder
    public UserEmailDto(String email) {
        this.email = email;
    }

}
