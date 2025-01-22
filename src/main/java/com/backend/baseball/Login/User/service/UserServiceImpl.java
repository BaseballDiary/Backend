package com.backend.baseball.Login.User.service;

import com.backend.baseball.Login.User.controller.response.UserEmailResponse;
import com.backend.baseball.Login.entity.User;
import com.backend.baseball.Login.User.dto.UserEmailDto;
import com.backend.baseball.Login.User.dto.UserJoinDto;
import com.backend.baseball.Login.User.dto.UserPasswordChangeDto;
import com.backend.baseball.Login.User.dto.UserPasswordResetDto;
import com.backend.baseball.Login.User.exception.*;
import com.backend.baseball.Login.User.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@Service
@Builder
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User getByCertificateId(Long certificateId) {
        User user=userRepository.findByCertificateId(certificateId).orElseThrow(()->
                new ResourceNotFoundException("User",certificateId));
        return user;
    }

    @Override
    @Transactional
    public User join(UserJoinDto userJoinDto) {
        String passwordConfirm=userJoinDto.getPasswordConfirm();
        if(!passwordConfirm.equals(userJoinDto.getPassword())){
            throw new ConfirmPasswordMismatchException();
        }
        User joinUser=userJoinDto.toEntity();

        String encodedPassword=passwordEncoder.encode(joinUser.getPassword());
        joinUser.changeUserPassword(encodedPassword);

        Optional<User> user=userRepository.findByEmail(userJoinDto.getEmail());
        if(!user.isEmpty()){
            throw new EmailAlreadyExistsException();
        }

        return userRepository.save(joinUser);
    }

    @Override
    public User changeUserPassword(Long certificateId, UserPasswordChangeDto userPasswordChangeDto) {
        User user=this.getByCertificateId(certificateId);
        if(!userPasswordChangeDto.getNewPassword().equals(userPasswordChangeDto.getNewPasswordConfirm())){
            throw new ConfirmPasswordMismatchException();
        }
        if(!passwordEncoder.matches(userPasswordChangeDto.getNewPassword(),user.getPassword())){
            throw new CurrentPasswordMismatchException();
        }

        String encodedPassword= passwordEncoder.encode(userPasswordChangeDto.getNewPassword());
        user.changeUserPassword(encodedPassword);

        return userRepository.save(user);
    }

    @Override
    public UserEmailResponse getUserEmail(Long certificateId) {
        User user= this.getByCertificateId(certificateId);
        if(user.getEmail()==null){
            throw new InvalidUserDataException();
        }
        return UserEmailResponse.builder()
                .email(user.getEmail())
                .build();
    }

    @Override
    public User resetUserPassword(UserPasswordResetDto userPasswordResetDto) {
        Optional<User> user=userRepository.findByEmail(userPasswordResetDto.getEmail());

        if(!userPasswordResetDto.getNewPassword().equals(userPasswordResetDto.getNewPasswordConfirm())){
            throw new ConfirmPasswordMismatchException();
        }

        String encodedPassword= passwordEncoder.encode(userPasswordResetDto.getNewPassword());
        user.get().changeUserPassword(encodedPassword);

        return userRepository.save(user.get());
    }

    @Override
    public Boolean confirmDupEmail(UserEmailDto userEmailDto) {
        Optional<User> user=userRepository.findByEmail(userEmailDto.getEmail());
        return user.isEmpty();
    }
}
