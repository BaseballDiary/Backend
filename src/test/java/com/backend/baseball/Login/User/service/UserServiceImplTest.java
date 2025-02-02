package com.backend.baseball.Login.User.service;

import com.backend.baseball.Login.User.controller.response.UserEmailResponse;
import com.backend.baseball.Login.User.dto.UserEmailDto;
import com.backend.baseball.Login.User.dto.UserJoinDto;
import com.backend.baseball.Login.User.dto.UserPasswordChangeDto;
import com.backend.baseball.Login.User.dto.UserPasswordResetDto;
import com.backend.baseball.Login.User.exception.*;
import com.backend.baseball.Login.User.repository.UserRepository;
import com.backend.baseball.Login.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

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
    void getByCertificateId_UserExists_ReturnsUser() {
        when(userRepository.findByCertificateId(1L)).thenReturn(Optional.of(testUser));

        User user = userService.getByCertificateId(1L);

        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
        verify(userRepository, times(1)).findByCertificateId(1L);
    }

    @Test
    void getByCertificateId_UserDoesNotExist_ThrowsException() {
        when(userRepository.findByCertificateId(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getByCertificateId(1L));
        verify(userRepository, times(1)).findByCertificateId(1L);
    }




    @Test
    void changeUserPassword_ValidInput_ChangesPassword() {
        UserPasswordChangeDto passwordChangeDto = new UserPasswordChangeDto("password", "newPassword", "newPassword");

        when(userRepository.findByCertificateId(1L)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(passwordChangeDto.getCurrentPassword(), testUser.getPassword())).thenReturn(true); // 기존 비밀번호가 일치하도록 설정
        when(passwordEncoder.encode(passwordChangeDto.getNewPassword())).thenReturn("newEncodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser); // save()가 정상적으로 호출될 수 있도록 설정

        User updatedUser = userService.changeUserPassword(1L, passwordChangeDto);

        assertNotNull(updatedUser);
        assertEquals("newEncodedPassword", updatedUser.getPassword());
        verify(userRepository, times(1)).save(testUser);
    }



    @Test
    void changeUserPassword_InvalidCurrentPassword_ThrowsException() {
        UserPasswordChangeDto passwordChangeDto = new UserPasswordChangeDto("wrongPassword", "newPassword", "newPassword");

        when(userRepository.findByCertificateId(1L)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(passwordChangeDto.getCurrentPassword(), testUser.getPassword())).thenReturn(false);

        assertThrows(CurrentPasswordMismatchException.class, () -> userService.changeUserPassword(1L, passwordChangeDto));
    }

    @Test
    void resetUserPassword_ValidInput_ResetsPassword() {
        UserPasswordResetDto resetDto = new UserPasswordResetDto("test@example.com", "newPassword", "newPassword");

        when(userRepository.findByEmail(resetDto.getEmail())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.encode(resetDto.getNewPassword())).thenReturn("newEncodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser); // save()가 정상적으로 호출될 수 있도록 설정

        User updatedUser = userService.resetUserPassword(resetDto);

        assertNotNull(updatedUser);
        assertEquals("newEncodedPassword", updatedUser.getPassword());
        verify(userRepository, times(1)).save(testUser);
    }


    @Test
    void resetUserPassword_EmailNotFound_ThrowsException() {
        UserPasswordResetDto resetDto = new UserPasswordResetDto("notfound@example.com", "newPassword", "newPassword");

        when(userRepository.findByEmail(resetDto.getEmail())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.resetUserPassword(resetDto));
    }


}
