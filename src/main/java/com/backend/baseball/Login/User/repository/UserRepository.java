package com.backend.baseball.Login.User.repository;

import com.backend.baseball.Login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCertificateId(Long certificateId);
    Optional<User> findByEmail(String email);
    User save(User user);

    User findByNickname(String nickname);
}
