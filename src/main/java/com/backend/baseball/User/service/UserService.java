package com.backend.baseball.User.service;

import com.backend.baseball.User.entity.User;

public interface UserService {
    //회원 데이터 저장
    void save(String email, String password);

    //회원 데이터 조회
    User find(String email, String password);
}
