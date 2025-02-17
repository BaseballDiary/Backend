package com.backend.baseball.User.repository;

import com.backend.baseball.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByNickname(String nickname);
    //이메일과 패스워드로 회원 정보를 조회
    Optional<User> findByEmailAndPassword(String email, String password);

    //이메일로 사용자 조회
    Optional<User> findByEmail(String email);
}
