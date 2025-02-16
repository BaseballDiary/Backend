package com.backend.baseball.User.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDTO {
    private String email;
    private String password;
    private String passwordConfirm;
}
