package com.backend.baseball.Login.User.controller.response;

import lombok.Data;

@Data
public class CreateUserResponse {

    private Long certificateId;
    private String message;

    public CreateUserResponse(String message) {
        this.message = message;
    }

    public CreateUserResponse(Long certificateId, String message) {
        this.certificateId = certificateId;
        this.message = message;
    }
}
