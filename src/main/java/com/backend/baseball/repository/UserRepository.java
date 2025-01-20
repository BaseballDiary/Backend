package com.backend.baseball.repository;

import com.backend.baseball.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(String id);

    Optional<User> findByEmail(String email);

    User findByNickname(String nickname);

    boolean existsById(String id);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

}
