package com.backend.baseball.security.auth;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import com.backend.baseball.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    /*유저의 권한 목록*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors=new ArrayList<>();

        collectors.add(()->"ROLE_"+user.getRole().name());

        return collectors;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getId(); // 아이디를 반환하기
    }

    /*계정 만료 여부
    * true: 만료 안 됨
    * false: 만료*/
    @Override
    public boolean isAccountNonExpired() {
        ;return true;
    }

    /*계정 잠김 여부
    * true: 잠기지 않음
    * false: 잠김*/
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*비밀번호 만료 여부
    true: 만료 안 됨
    false: 만료됨
    * */
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    /*사용자 활성화 여부
    * true: 만료 안 됨.
    * false: 만료*/
    @Override
    public boolean isEnabled() {
        return true;
    }
}
