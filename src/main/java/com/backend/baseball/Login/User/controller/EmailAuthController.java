package com.backend.baseball.Login.User.controller;

import com.backend.baseball.Login.entity.User;
import com.backend.baseball.Login.User.dto.UserPasswordResetDto;
import com.backend.baseball.Login.User.emailauth.MailAddressDto;
import com.backend.baseball.Login.User.emailauth.MailCertificationDto;
import com.backend.baseball.Login.User.emailauth.MailCertificationResponse;
import com.backend.baseball.Login.User.emailauth.MailServiceImpl;
import com.backend.baseball.Login.User.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class EmailAuthController {
    private final MailServiceImpl mailService;
    private final UserServiceImpl userService;


    @PostMapping("/auth")
    public ResponseEntity<MailCertificationResponse> joinSendMail(@RequestBody @Valid MailAddressDto mailAddressDto) {
        MailCertificationResponse mailCertificationResponse = mailService.sendMailJoin(mailAddressDto.getEmail());
        return ResponseEntity.ok(mailCertificationResponse);
    }

    @PostMapping("/password/send")
    public ResponseEntity<MailCertificationResponse> resetPasswordSendMail(@RequestBody @Valid MailAddressDto mailAddressDto) {
        MailCertificationResponse mailCertificationResponse=mailService.sendMailPasswordReset(mailAddressDto.getEmail());
        return ResponseEntity.ok(mailCertificationResponse);
    }

    @PostMapping({"/auth/confirm","/password/confirm"})
    public ResponseEntity<Boolean> confirmMailNumber(@RequestBody @Valid MailCertificationDto mailCertificationDto){
        return ResponseEntity.ok(mailService.verifyEmail(mailCertificationDto));
    }

    @PostMapping("/password/reset")
    public ResponseEntity<Boolean> resetUserPassword(@RequestBody @Valid UserPasswordResetDto userPasswordResetDto){
        User user=userService.resetUserPassword(userPasswordResetDto);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
