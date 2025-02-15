package com.backend.baseball.User.entity;

import com.backend.baseball.Diary.entity.Diary;
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
public class User implements UserDetails {
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
    @Override //권환 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority("user"));
    }

    //사용자의 id를 반환 - 고유한 값
    @Override
    public String getUsername(){
        return email;
    }

    //사용자의 패스워드 반환
    @Override
    public String getPassword(){
        return password;
    }

    //계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired(){
        //만료되었는지 확인하는 로직
        return true; //true -> 만료되지 않았음
    }

    //계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked(){
        return true; //true -> 잠금아님
    }

    //패스워드 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired(){
        //패스워드가 만료되었는지 확인하는 로직
        return true; //true -> 만료되지 않음
    }

    //계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled(){
        //계정이 사용 가능한지 확인하는 로직
        return true; //true -> 사용 가능
    }

}
