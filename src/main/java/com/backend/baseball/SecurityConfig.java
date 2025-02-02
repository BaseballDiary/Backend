package com.backend.baseball;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {  //localhost 8080 login 화면 없앰

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .csrf((csrf) -> csrf.disable())

                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .permitAll()
                )
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        //        .anyRequest().authenticated()) // 다른 모든 요청은 인증 필요


                        .requestMatchers("/resources/**").permitAll()
                        .anyRequest().permitAll()) // 다른 모든 요청은 인증 필요
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                        .disable()) // /h2-console 경로에 대해 CSRF 보호 비활성화

                .headers(
                        headersConfigurer ->
                                headersConfigurer
                                        .frameOptions(
                                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                        ));

        return http.build();
    }
}