package com.backend.baseball.User.repository;

import com.backend.baseball.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); //email로 사용자 정보 가져옴
    Optional<User> findByCertificateId(Long certificateId); // certificateId로 사용자 조회

}
