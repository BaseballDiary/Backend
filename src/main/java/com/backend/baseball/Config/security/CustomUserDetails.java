package com.backend.baseball.Config.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {
    private final Long certificationId; // ✅ 인증 ID (User의 certificateId와 연결)

    public CustomUserDetails(Long certificationId) { // ✅ 생성자 수정
        this.certificationId = certificationId;
    }

    public Long getCertificationId() { // ✅ 이 메서드가 없으면 오류 발생
        return certificationId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return certificationId.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
