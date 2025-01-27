package com.backend.baseball.Login.User.exception;

public class InvalidUserDataException extends RuntimeException {
    public InvalidUserDataException() {
        super("유저 정보는 null일 수 없습니다.");
    }
}
