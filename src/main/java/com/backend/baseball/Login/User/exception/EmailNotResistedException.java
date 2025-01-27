package com.backend.baseball.Login.User.exception;

public class EmailNotResistedException extends RuntimeException {
    public EmailNotResistedException() {super("등록되지 않은 이메일입니다.");}
}
