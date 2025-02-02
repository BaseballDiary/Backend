package com.backend.baseball.Login.User.controller;

import com.backend.baseball.Login.User.controller.response.CreateUserResponse;
import com.backend.baseball.Login.User.dto.MyPagePasswordAuthDto;
import com.backend.baseball.Login.User.dto.UserEmailDto;
import com.backend.baseball.Login.User.dto.UserJoinDto;
import com.backend.baseball.Login.User.dto.UserPasswordChangeDto;
import com.backend.baseball.Login.User.emailauth.MailServiceImpl;
import com.backend.baseball.Login.User.service.UserService;
import com.backend.baseball.Login.login.argumentresolver.Login;
import com.backend.baseball.Login.login.controller.dto.LoginInfo;
import com.backend.baseball.Login.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.baseball.Login.entity.User;

@RequestMapping("/user")
@Builder
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MailServiceImpl mailService;
    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<CreateUserResponse> saveUser(
            @RequestBody @Valid UserJoinDto userJoinDto,
            HttpServletRequest request){
        User joinUser=userService.join(userJoinDto);

        loginService.makeLoginSession(LoginInfo.from(joinUser),request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CreateUserResponse(joinUser.getCertificateId(),"User created successfully"));
    }

    @PostMapping("/confirmEmail")
    public ResponseEntity<Boolean> confirmEmail(@RequestBody UserEmailDto userEmailDto){
        Boolean result=mailService.confirmDupEmail(userEmailDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("myPage/password")
    public ResponseEntity<Boolean> changeUserPassword(@Login LoginInfo loginInfo
    , @RequestBody @Valid UserPasswordChangeDto userPasswordChangeDto){
        userService.changeUserPassword(loginInfo.getCertificationId(),userPasswordChangeDto);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/myPage")
    public ResponseEntity<Boolean> myPagePasswordAuth(@Login LoginInfo loginInfo,
                                                      @RequestBody @Valid MyPagePasswordAuthDto myPagePasswordAuthDto){
        userService.myPagePasswordAuth(loginInfo.getCertificationId(),myPagePasswordAuthDto);
        return ResponseEntity.ok(true);
    }


}
