package com.backend.baseball.Login.login.service;

import com.backend.baseball.Login.User.repository.UserRepository;
import com.backend.baseball.Login.entity.User;
import com.backend.baseball.Login.login.controller.SessionConst;
import com.backend.baseball.Login.login.controller.dto.LoginInfo;
import com.backend.baseball.Login.login.exception.IncorrectPasswordException;
import com.backend.baseball.Login.login.exception.UserEmailNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private LoginService loginService;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = User.builder()
                .certificateId(1L)
                .email("test@example.com")
                .password("encodedPassword")
                .build();
    }

    @Test
    void login_ValidCredentials_ReturnsLoginInfo() {
        String rawPassword = "password";

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(rawPassword, testUser.getPassword())).thenReturn(true);

        LoginInfo loginInfo = loginService.login("test@example.com", rawPassword);

        assertNotNull(loginInfo);
        assertEquals(1L, loginInfo.getCertificationId());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void login_InvalidEmail_ThrowsUserEmailNotFoundException() {
        when(userRepository.findByEmail("invalid@example.com")).thenReturn(Optional.empty());

        assertThrows(UserEmailNotFoundException.class, () -> loginService.login("invalid@example.com", "password"));
        verify(userRepository, times(1)).findByEmail("invalid@example.com");
    }

    @Test
    void login_InvalidPassword_ThrowsIncorrectPasswordException() {
        String rawPassword = "wrongPassword";

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(rawPassword, testUser.getPassword())).thenReturn(false);

        assertThrows(IncorrectPasswordException.class, () -> loginService.login("test@example.com", rawPassword));
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void makeLoginSession_ValidLoginInfo_SetsSessionAttribute() {
        LoginInfo loginInfo = LoginInfo.builder()
                .certificationId(1L)
                .build();

        when(request.getSession(true)).thenReturn(session);

        loginService.makeLoginSession(loginInfo, request);

        verify(request, times(1)).getSession(true);
        verify(session, times(1)).setAttribute(SessionConst.LOGIN_USER_INFO, loginInfo);
    }

    @Test
    void makeLoginSession_NullRequest_ThrowsNullPointerException() {
        LoginInfo loginInfo = LoginInfo.builder()
                .certificationId(1L)
                .build();

        assertThrows(NullPointerException.class, () -> loginService.makeLoginSession(loginInfo, null));
    }
}
