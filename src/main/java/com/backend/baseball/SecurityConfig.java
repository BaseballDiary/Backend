package com.backend.baseball;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {  // localhost 8080 기본 로그인 화면 제거

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/logout").permitAll() // ✅ 로그인 & 로그아웃 엔드포인트 허용
                        .requestMatchers("/resources/**").permitAll()
                        .anyRequest().permitAll() // ✅ 다른 모든 요청 허용 (보안 강화 필요시 authenticated()로 변경)
                )

                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                        .disable()) // ✅ H2 콘솔 CSRF 비활성화

                .headers(headersConfigurer ->
                        headersConfigurer
                                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )

                .formLogin(formLogin -> formLogin.disable()); // ✅ 기본 로그인 페이지 제거

        return http.build();
    }
}
