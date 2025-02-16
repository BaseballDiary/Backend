package com.backend.baseball.User.helper;

import com.backend.baseball.User.dto.LoginRequestDTO;
import com.backend.baseball.User.dto.SignupRequestDTO;
import com.backend.baseball.User.entity.User;
import com.backend.baseball.User.etc.AccountConstants;
import com.backend.baseball.User.service.UserService;
import com.backend.baseball.util.HttpUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionAccountHelper implements AccountHelper{

    private final UserService userService;

    //회원가입
    @Override
    public void join(SignupRequestDTO joinReq){
        userService.save(joinReq.getEmail(), joinReq.getPassword());
    }

    //로그인
    @Override
    public String login(LoginRequestDTO loginReq, HttpServletRequest req, HttpServletResponse res){
        User user = userService.find(loginReq.getEmail(), loginReq.getPassword());

        //회원 데이터가 없으면
        if(user == null){
            return null;
        }

        HttpUtils.setSession(req, AccountConstants.MEMBER_ID_NAME, user.getCertificateId());
        return user.getEmail();
    }

    //회원 아이디 조회
    @Override
    public Long getMemberId(HttpServletRequest req){
        Object memberId = HttpUtils.getSessionValue(req, AccountConstants.MEMBER_ID_NAME);
        if(memberId !=null){
            return (Long) memberId;
        }
        return null;
    }


    //로그인 여부 확인
    @Override
    public boolean isLoggedIn(HttpServletRequest req){
        return getMemberId(req) != null;
    }


    //로그아웃
    @Override
    public void logout(HttpServletRequest req, HttpServletResponse res){
        HttpUtils.removeSession(req, AccountConstants.MEMBER_ID_NAME);
    }

}
