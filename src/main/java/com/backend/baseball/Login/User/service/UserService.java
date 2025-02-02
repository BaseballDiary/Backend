package com.backend.baseball.Login.User.service;

import com.backend.baseball.Login.User.controller.response.UserEmailResponse;
import com.backend.baseball.Login.User.dto.*;
import com.backend.baseball.Login.entity.User;

public interface UserService {
    User getByCertificateId(Long certificateId);
    User join(UserJoinDto userJoinDto);
    User changeUserPassword(Long certificateId, UserPasswordChangeDto userPasswordChangeDto);
    User myPagePasswordAuth(Long certificateId, MyPagePasswordAuthDto myPagePasswordAuthDto);
    UserEmailResponse getUserEmail(Long certificateId);
    User resetUserPassword(UserPasswordResetDto userPasswordResetDto);
    Boolean confirmDupEmail(UserEmailDto userEmailDto);
}
