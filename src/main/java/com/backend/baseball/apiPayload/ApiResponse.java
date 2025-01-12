package com.backend.baseball.apiPayload;

import com.backend.baseball.apiPayload.code.BaseCode;
import com.backend.baseball.apiPayload.code.status.SuccessStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter // Lombok 라이브러리 사용하여 모든 필드에 대한 getter 메서드 자동 생성
@AllArgsConstructor // Lombok 사용하여 모든 필드를 포함하는 생성자 자동 생성
@JsonPropertyOrder({"isSuccess", "code", "message", "result"}) // JSON 응답에서 필드가 나오는 순서 지정
public class ApiResponse<T> {

    @JsonProperty("isSuccess") // JSON 필드 이름을 isSuccess로 출력되도록 지정
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result; // 여기에 응답결과 저장


    // 성공한 경우 응답 생성

    public static <T> ApiResponse<T> onSuccess(T result){
        return new ApiResponse<>(true, SuccessStatus._OK.getCode() , SuccessStatus._OK.getMessage(), result);
    }

    public static <T> ApiResponse<T> of(BaseCode code, T result){
        return new ApiResponse<>(true, code.getReasonHttpStatus().getCode() , code.getReasonHttpStatus().getMessage(), result);
    }


    // 실패한 경우 응답 생성
    public static <T> ApiResponse<T> onFailure(String code, String message, T data){
        return new ApiResponse<>(false, code, message, data); // 타입추론가능해서 <T>라고 안적어도 됨.
    }
}