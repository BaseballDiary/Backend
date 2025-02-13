package com.backend.baseball.Login.User.controller;

import com.backend.baseball.Login.User.controller.response.CreateUserResponse;
import com.backend.baseball.Login.User.dto.*;
import com.backend.baseball.Login.User.emailauth.MailServiceImpl;
import com.backend.baseball.Login.User.service.UserService;
import com.backend.baseball.Login.login.argumentresolver.Login;
import com.backend.baseball.Login.login.controller.dto.LoginInfo;
import com.backend.baseball.Login.login.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.backend.baseball.Login.entity.User;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User API", description = "회원 관리 API")
public class UserController {

    private final UserService userService;
    private final MailServiceImpl mailService;
    private final LoginService loginService;

    @Operation(summary = "회원 가입", description = "새로운 사용자를 등록합니다.")
    @PostMapping
    public ResponseEntity<CreateUserResponse> saveUser(
            HttpServletResponse response,
            @RequestBody @Valid UserJoinDto userJoinDto,
            HttpServletRequest request) {
        User joinUser = userService.join(userJoinDto);
        loginService.makeLoginSession(LoginInfo.from(joinUser), request,response);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateUserResponse(joinUser.getCertificateId(), "User created successfully"));
    }
    /*
    @PostMapping("/selectNickname")
    public ResponseEntity<String> selectNickname(@Login LoginInfo loginInfo,@RequestBody UserInfoChangeDto userInfoChangeDto){
        userService.updateNickname();
    }
    */


    @Operation(summary = "이메일 중복 확인", description = "입력한 이메일이 이미 사용 중인지 확인합니다.")
    @PostMapping("/confirmEmail")
    public ResponseEntity<Boolean> confirmEmail(@RequestBody UserEmailDto userEmailDto) {
        Boolean result = mailService.confirmDupEmail(userEmailDto);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "비밀번호 변경", description = "로그인한 사용자의 비밀번호를 변경합니다.")
    @PostMapping("/myPage/password")
    public ResponseEntity<Boolean> changeUserPassword(
            @Login LoginInfo loginInfo,
            @RequestBody @Valid UserPasswordChangeDto userPasswordChangeDto) {
        userService.changeUserPassword(loginInfo.getCertificationId(), userPasswordChangeDto);
        return ResponseEntity.ok(true);
    }

    @Operation(summary = "비밀번호 인증", description = "마이페이지 접근을 위한 비밀번호 인증을 수행합니다.")
    @PostMapping("/myPage")
    public ResponseEntity<Boolean> myPagePasswordAuth(
            @Login LoginInfo loginInfo,
            @RequestBody @Valid MyPagePasswordAuthDto myPagePasswordAuthDto) {
        userService.myPagePasswordAuth(loginInfo.getCertificationId(), myPagePasswordAuthDto);
        return ResponseEntity.ok(true);
    }
}
