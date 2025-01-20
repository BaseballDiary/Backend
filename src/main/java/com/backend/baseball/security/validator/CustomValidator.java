package com.backend.baseball.security.validator;

import com.backend.baseball.Login.dto.UserDto;
import com.backend.baseball.Login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/*중복 확인 유효성 검증을 위한 커스텀 Validator 클래스*/
@RequiredArgsConstructor
@Component
public class CustomValidator {

    private final UserRepository userRepository;

    @RequiredArgsConstructor
    @Component
    public static class EmailValidator extends AbstractValidator<UserDto.Request>{
        private final UserRepository userRepository;

        @Override
        protected void doValidate(UserDto.Request dto, Errors errors) {
            if(userRepository.existsByEmail(dto.toEntity().getEmail())){
                errors.rejectValue("email","이메일 중복 오류","이미 사용중인 이메일입니다.");
            }
        }
    }

    @RequiredArgsConstructor
    @Component
    public static class NicknameValidator extends AbstractValidator<UserDto.Request>{

        private final UserRepository userRepository;
        @Override
        protected void doValidate(UserDto.Request dto, Errors errors) {
            if(userRepository.existsByNickname(dto.toEntity().getNickname())){
                errors.rejectValue("nickname","닉네임 중복 오류","이미 사용 중인 닉네임입니다.");
            }
        }
    }

    @RequiredArgsConstructor
    @Component
    public static class IdValidator extends AbstractValidator<UserDto.Request>{
        private final UserRepository userRepository;
        @Override
        protected void doValidate(UserDto.Request dto, Errors errors) {
            if(userRepository.existsById(dto.toEntity().getId())){
                errors.rejectValue("id","아이디 중복 오류","이미 사용 중인 아이디입니다.");
            }
        }
    }
}
