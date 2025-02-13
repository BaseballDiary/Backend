package com.backend.baseball.Community.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class LikesRequestDto {
    private Long certificateId;
    private Long postCertificateId;

    public LikesRequestDto(Long certificateId,Long postCertificateId){
        this.certificateId = certificateId;
        this.postCertificateId = postCertificateId;
    }
}
