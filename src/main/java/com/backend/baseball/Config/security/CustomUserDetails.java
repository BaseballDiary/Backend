package com.backend.baseball.Config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private final Long certificationId;  // 기존: ID 기반
    private final String email;  // ✅ 추가


    public CustomUserDetails(Long certificationId, String email) { // ✅ email 추가
        this.certificationId = certificationId;
        this.email = email;

    }

    @Override
    public String getUsername() {
        return email; // ✅ UserDetails의 username은 email로 사용
        // ✅ SPRING_SESSION.PRINCIPAL_NAME 컬럼에 저장됨
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // 권한 없음
    }

    @Override
    public String getPassword() {
        return null; // 사용하지 않음
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
