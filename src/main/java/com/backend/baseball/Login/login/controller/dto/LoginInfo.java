package com.backend.baseball.Login.login.controller.dto;

import com.backend.baseball.Login.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class LoginInfo implements Serializable {
    private Long certificationId;
    private String email;  // ✅ email 추가 (SecurityContextHolder에 저장할 필드)

    public static LoginInfo from(User user) {
        return LoginInfo.builder()
                .certificationId(user.getCertificateId())
                .email(user.getEmail())  // ✅ email 포함
                .build();
    }
}
