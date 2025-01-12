package com.backend.baseball.apiPayload.code;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ReasonDTO {
    private HttpStatus httpStatus;

    private final boolean isSuccess;
    private final String code; // private지만 Lombok의 @Getter 덕분에 자동으로 getter 생성해서 외부에서도 접근 가능
    private final String message;

    public boolean getIsSuccess(){return isSuccess;}
}
