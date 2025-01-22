package com.backend.baseball.Login.User.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
public class UserEmailResponse {
    private String email;

    public UserEmailResponse() {

    }

    @Builder
    public UserEmailResponse(String email) {
        this.email = email;
    }

}
