package com.backend.baseball;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {  // localhost 8080 기본 로그인 화면 제거
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/logout").permitAll() // 로그인 & 로그아웃은 누구나 가능
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) // ✅ 세션 유지

                .formLogin(withDefaults()); // ✅ 기본 로그인 유지 (Swagger에서 세션 유지 필요)

        return http.build();
    }


}
