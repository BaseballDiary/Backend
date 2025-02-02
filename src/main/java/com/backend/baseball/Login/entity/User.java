package com.backend.baseball.Login.entity;

import com.backend.baseball.Diary.entity.Diary;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


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


    
    @NotNull
    @Column(nullable = false)
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



}
