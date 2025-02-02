package com.backend.baseball;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ CORS 설정 활성화
                .csrf(csrf -> csrf.disable()) // ✅ CSRF 비활성화 (필요한 경우)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()  // ✅ Swagger 허용
                        .requestMatchers("/h2-console/**").permitAll()  // ✅ H2 콘솔 접근 허용
                        .requestMatchers("/user/**", "/auth/**", "/password/**").permitAll()  // ✅ 사용자 및 인증 관련 요청 허용
                        .requestMatchers(HttpMethod.GET, "/user/**").permitAll() // ✅ 모든 GET 요청 허용
                        .anyRequest().authenticated()) // 🔐 그 외 모든 요청은 인증 필요
                .formLogin(form -> form
                        .loginPage("/login") // ✅ 로그인 페이지 설정
                        .defaultSuccessUrl("/", true) // ✅ 로그인 성공 후 리디렉션할 기본 페이지 설정
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())); // ✅ H2 콘솔 접근 가능하도록 설정

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // ✅ React 프론트엔드 도메인만 허용
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // ✅ 모든 HTTP 메서드 허용
        configuration.setAllowedHeaders(List.of("*")); // ✅ 모든 헤더 허용
        configuration.setAllowCredentials(true); // ✅ 인증 정보 포함 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
