package com.backend.baseball.User.entity;

//import com.backend.baseball.Diary.entity.Diary;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class User{
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

    //@OneToMany(mappedBy = "user") //관계설정
    //private List<Diary> diaries = new ArrayList<>();

    // 닉네임 변경
    public void changeNickname(String newNickname) {
            this.nickname = newNickname;
        }

    // 클럽 변경
    public void changeClub(String newClub) {
        this.myClub = newClub;
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

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }


}
