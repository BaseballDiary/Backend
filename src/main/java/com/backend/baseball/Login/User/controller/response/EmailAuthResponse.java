package com.backend.baseball.Login.User.controller.response;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

@Data
public class EmailAuthResponse {

    private String email;
    private int authNumber;

    public EmailAuthResponse(){

    }
    @Builder
    public EmailAuthResponse(String email, int authNumber) {
        this.email = email;
        this.authNumber = authNumber;
    }

}
