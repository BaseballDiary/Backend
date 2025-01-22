package com.backend.baseball.Login.User.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.backend.baseball.Login.entity.User;


@Data
@NoArgsConstructor
public class UserJoinDto {

    @NotNull @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String passwordConfirm;

    @Builder
    public UserJoinDto(String email, String password, String passwordConfirm) {
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public User toEntity(){
        User user=User.builder()
                .email(email)
                .password(password)
                .build();
        return user;
    }
}
