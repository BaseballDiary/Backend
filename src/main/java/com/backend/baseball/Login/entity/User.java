package com.backend.baseball.Login.entity;

import com.backend.baseball.Login.enums.Club;
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

    @Column(nullable = false,length = 30)
    private String password; // 패스워드

    @Column(nullable = false,length = 50,unique = true)
    private String email; // 이메일

    @Column(nullable = false,length = 30,unique = true)
    private String nickname; // 닉네임

    @Column(nullable = false,columnDefinition = "integer default 0")
    private int temperature; // 야구 온도

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Club myClub;



    /*회원 정보 수정하기(닉네임, 구단, 한줄 소개)*/
    // 개인 정보 변경하는 것도 있어야 하는 거 아닌가..? 내일 건의드려 봐야지
    public void modify(String nickname, Club myClub){
        this.nickname = nickname;
        this.myClub = myClub;
    }

}
