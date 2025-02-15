package com.backend.baseball.Login.login.service;

//import com.backend.baseball.Config.security.CustomUserDetails;
import com.backend.baseball.Login.entity.User;
import com.backend.baseball.Login.User.repository.UserRepository;
import com.backend.baseball.Login.login.controller.SessionConst;
import com.backend.baseball.Login.login.exception.IncorrectPasswordException;
import com.backend.baseball.Login.login.exception.UserEmailNotFoundException;
import com.backend.baseball.Login.login.controller.dto.LoginInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Builder
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginInfo login(String email, String rawPassword) {
        // 이메일로 유저 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserEmailNotFoundException::new);
        validationLoginPassword(user, rawPassword);

        // 로그인 성공 후 LoginInfo 반환
        return LoginInfo.from(user);
    }

    public void makeLoginSession(LoginInfo loginInfo, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true); // 세션 생성 또는 기존 세션 사용

        session.setAttribute(SessionConst.LOGIN_USER_INFO, loginInfo);

        // JSESSIONID 쿠키 설정
        String jsessionid = session.getId();
        String contextPath = request.getContextPath();
        // ✅ HTTPS 환경 확인
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
