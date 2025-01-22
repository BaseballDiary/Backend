package com.backend.baseball.Login.login.exception;

public class UserEmailNotFoundException extends RuntimeException {
    public UserEmailNotFoundException() {
        super("이메일로 등록된 사용자를 찾을 수 없습니다.");
    }
}
