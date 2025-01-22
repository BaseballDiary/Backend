package com.backend.baseball.Login.User.emailauth;

import com.backend.baseball.Login.User.dto.UserEmailDto;

public interface MailService {
    public MailCertificationResponse sendMailPasswordReset(String email);
    public MailCertificationResponse sendMailJoin(String email);

    Boolean verifyEmail(MailCertificationDto requestDto);
    Boolean confirmDupEmail(UserEmailDto userEmailDto);

}
