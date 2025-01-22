package com.backend.baseball.Login.login.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("로그인이 필요한 요청입니다.");
    }
}
