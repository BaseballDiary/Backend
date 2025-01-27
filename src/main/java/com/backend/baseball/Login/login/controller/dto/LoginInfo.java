package com.backend.baseball.Login.login.controller.dto;

import com.backend.baseball.Login.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginInfo {
    private Long certificationId;

    public static LoginInfo from(User user){
        return LoginInfo.builder()
                .certificationId(user.getCertificateId())
                .build();
    }

}
