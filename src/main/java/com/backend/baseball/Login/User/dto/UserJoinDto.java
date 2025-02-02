package com.backend.baseball.Login.User.dto;

import com.backend.baseball.Login.enums.Club;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.backend.baseball.Login.entity.User;


@Data
@NoArgsConstructor
public class UserJoinDto {

    @Schema(description = "사용자 이메일", example = "example@gmail.com")
    @NotBlank(message = "이메일을 입력해야 합니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @Schema(description = "사용자 비밀번호", example = "hong1234")
    @NotBlank(message = "비밀번호를 입력해야 합니다.")
    @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다.")
    private String password;

    @Schema(description = "비밀번호 확인", example = "hong1234")
    @NotBlank(message = "비밀번호 확인을 입력해야 합니다.")
    private String passwordConfirm;



    @Builder
    public UserJoinDto(String email, String password, String passwordConfirm, String myClub) {
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public User toEntity(){
        User user=User.builder()
                .email(email)
                .password(password)
                .build();
        return user;
    }
}
