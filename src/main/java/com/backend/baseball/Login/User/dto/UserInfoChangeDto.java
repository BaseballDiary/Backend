package com.backend.baseball.Login.User.dto;

import com.backend.baseball.Login.enums.Club;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoChangeDto {
    @NotNull
    private String nickname;
    @NotNull
    private Club myClub;

    @Builder
    public UserInfoChangeDto(String nickname, Club myClub) {
        this.nickname = nickname;
        this.myClub = myClub;
    }

}
