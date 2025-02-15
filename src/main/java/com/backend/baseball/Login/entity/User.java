package com.backend.baseball.Login.entity;


import com.backend.baseball.Diary.entity.Diary;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificateId; // 회원식별번호

    @Column(nullable = false,length = 300)
    private String password; // 패스워드

    @Column(nullable = false,length = 50,unique = true)
    private String email; // 이메일

    @Column(nullable = true,length = 30)
    private String nickname; // 닉네임

    @Column(nullable = false,columnDefinition = "integer default 0")
    private int temperature; // 야구 온도


    
    @Column(nullable = true)
    private String myClub;

    @OneToMany(mappedBy = "user") //관계설정
    private List<Diary> diaries = new ArrayList<>();




    // 닉네임 변경
    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
    }

    // 클럽 변경
    public void changeClub(String newClub) {
        this.myClub = newClub;
    }

    // 비밀번호 변경
    public void changeUserPassword(String password){
        this.password = password;
    }

    //myClub, nicknames 기본값 설정
    @PrePersist
    public void prePersist() {
        if (this.myClub == null) {
            this.myClub = "default_club";
        }
        if (this.nickname == null) {
            this.nickname = "default_nickname";
        }
    }



}
