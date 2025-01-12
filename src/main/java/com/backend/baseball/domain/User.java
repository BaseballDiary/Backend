package com.backend.baseball.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificateId; // 회원식별번호

    @Column(nullable = false,length = 30,unique = true)
    private String id; //아이디

    @Column(nullable = false,length = 30)
    private String password; // 패스워드

    @Column(nullable = false,length = 50,unique = true)
    private String email; // 이메일

    @Column(nullable = false,length = 30,unique = true)
    private String nickname; // 닉네임

    @Column(nullable = false,length = 30)
    private int temperature; // 야구 온도

}
