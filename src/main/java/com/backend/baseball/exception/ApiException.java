package com.backend.baseball.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    public ApiException(Exception e) {
        super(e.getMessage());
    }
}