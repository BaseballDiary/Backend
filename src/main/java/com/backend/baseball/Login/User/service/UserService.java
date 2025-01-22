package com.backend.baseball.Login.User.service;

import com.backend.baseball.Login.User.controller.response.UserEmailResponse;
import com.backend.baseball.Login.entity.User;
import com.backend.baseball.Login.User.dto.UserEmailDto;
import com.backend.baseball.Login.User.dto.UserJoinDto;
import com.backend.baseball.Login.User.dto.UserPasswordChangeDto;
import com.backend.baseball.Login.User.dto.UserPasswordResetDto;

public interface UserService {
    User getByCertificateId(Long certificateId);
    User join(UserJoinDto userJoinDto);
    User changeUserPassword(Long certificateId, UserPasswordChangeDto userPasswordChangeDto);
    UserEmailResponse getUserEmail(Long certificateId);
    User resetUserPassword(UserPasswordResetDto userPasswordResetDto);
    Boolean confirmDupEmail(UserEmailDto userEmailDto);
}
