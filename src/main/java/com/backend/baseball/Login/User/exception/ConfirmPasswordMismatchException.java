package com.backend.baseball.Login.User.exception;

public class ConfirmPasswordMismatchException extends RuntimeException {
    public ConfirmPasswordMismatchException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
