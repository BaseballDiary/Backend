package com.backend.baseball.User.dto;


import lombok.Getter;
import lombok.Setter;

//로그인 요청 DTO
@Getter
@Setter

public class LoginRequestDTO {
    private String email;
    private String password;
}
