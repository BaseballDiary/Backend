package com.backend.baseball.Login.dto;

import com.backend.baseball.Login.entity.User;
import com.backend.baseball.Login.enums.Club;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;

/**
 * request,response DTO 클래스를 하나로 묶어서 InnerStaticClass로 한꺼번에 관리한다.
 */


public class UserDto {
    /*회원 Service 요청(Request) DTO 클래스*/

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{

        private Long certificateId; // 회원식별번호



        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String password; // 패스워드

        @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        private String email; // 이메일

        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
        @NotBlank(message = "닉네임은 필수 입력 값입니다.")
        private String nickname; // 닉네임

        private int temperature; // 야구 온도

        private Club myClub;



        /*DTO -> Entity*/
        public User toEntity(){
            User user=User.builder()
                    .certificateId(certificateId)
                    .password(password)
                    .email(email)
                    .nickname(nickname)
                    .temperature(temperature)
                    .myClub(myClub)
                    .build();
            return user;
        }

    }

    @Getter
    public static class Response implements Serializable{
        private final Long certificateId;
        private final String id;
        private final String nickname;
        private final String email;
        private final Club myClub;


        /*Entity -> DTO*/
        public Response(User user){
            this.certificateId = user.getCertificateId();
            this.id = user.getId();
            this.nickname = user.getNickname();
            this.email = user.getEmail();
            this.myClub = user.getMyClub();

        }
    }
}
