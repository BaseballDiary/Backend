package com.backend.baseball;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {  // localhost 8080 기본 로그인 화면 제거

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ CORS 설정 적용

                .csrf(csrf -> csrf.disable()) // CSRF 비활성화

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()  // ✅ OPTIONS 요청 허용

                        .requestMatchers("/login", "/logout").permitAll() // ✅ 로그인 & 로그아웃 엔드포인트 허용
                        .requestMatchers("/resources/**").permitAll()
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll() //swagger 관련 요청 허용 코드
                        .anyRequest().permitAll() // ✅ 다른 모든 요청 허용 (보안 강화 필요시 authenticated()로 변경)
                )


                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                        .disable()) // ✅ H2 콘솔 CSRF 비활성화
                .sessionManagement(session->session
                        //.sessionFixation().migrateSession()
                         .sessionFixation().none()  // ✅ 세션 고정 방지, 기존 세션 유지
                                .maximumSessions(1) // ✅ 하나의 로그인 세션 유지
                                .maxSessionsPreventsLogin(true) // ✅ 새로운 로그인 시 기존 세션 유지 (로그아웃 X)
                         )

                .headers(headersConfigurer ->
                        headersConfigurer
                                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )

                .formLogin(formLogin -> formLogin.disable()); // ✅ 기본 로그인 페이지 제거

        return http.build();
    }
    // ✅ CORS 설정 추가-세연 추가
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://3.39.169.50:8080",
                "https://www.baseballdiary.shop"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("Set-Cookie")); // ✅ Set-Cookie 헤더 노출

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
