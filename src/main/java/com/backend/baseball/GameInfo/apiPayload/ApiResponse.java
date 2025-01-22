package com.backend.baseball.GameInfo.apiPayload;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"message", "data"})
public class ApiResponse<T> {
    private String message;
    private T data;
}