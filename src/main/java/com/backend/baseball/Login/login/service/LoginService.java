package com.backend.baseball.Login.login.service;

import com.backend.baseball.Config.security.CustomUserDetails;
import com.backend.baseball.Login.entity.User;
import com.backend.baseball.Login.User.repository.UserRepository;
import com.backend.baseball.Login.login.exception.IncorrectPasswordException;
import com.backend.baseball.Login.login.exception.UserEmailNotFoundException;
import com.backend.baseball.Login.login.controller.dto.LoginInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Builder
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginInfo login(String email, String rawPassword, HttpServletRequest request, HttpServletResponse response) {
        // ✅ 이메일로 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserEmailNotFoundException::new);

        // ✅ 비밀번호 검증
        validationLoginPassword(user, rawPassword);

        // ✅ 로그인 성공 후 세션 생성 및 SecurityContextHolder 설정
        LoginInfo loginInfo = LoginInfo.from(user);
        makeLoginSession(loginInfo, request, response);

        return loginInfo;
    }
    public void makeLoginSession(LoginInfo loginInfo, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true); // 기존 세션 유지

        // ✅ 수정된 생성자로 변경 (certificationId, email 전달)
        CustomUserDetails userDetails = new CustomUserDetails(loginInfo.getCertificationId(), loginInfo.getEmail());

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        session.setAttribute("LoginUser", loginInfo); // ✅ 세션에 사용자 정보 저장

        log.info("✅ 로그인 성공: {} (세션 ID: {})", loginInfo.getEmail(), session.getId());

        // ✅ JSESSIONID 쿠키 설정
        String jsessionid = session.getId();
        boolean isSecure = request.isSecure();
        String cookieHeader = "JSESSIONID=" + jsessionid + "; Path=/; HttpOnly; SameSite=None" + (isSecure ? "; Secure" : "");
        response.setHeader("Set-Cookie", cookieHeader);
    }



    private void validationLoginPassword(User user, String rawPassword) {
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IncorrectPasswordException();
        }
    }
}
