package com.backend.baseball.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {


    private Long id; // 야구 온도
    private String password; // 비밀번호
    private String email; // 이메일
    private String nickname; // 닉네임
    private String myclub; // 내 구단
    private int temperature; // 야구온도

}
