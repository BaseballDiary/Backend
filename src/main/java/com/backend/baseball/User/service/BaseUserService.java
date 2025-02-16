package com.backend.baseball.User.service;

import com.backend.baseball.User.entity.User;
import com.backend.baseball.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseUserService implements UserService{
    private final UserRepository userRepository;

    //회원 데이터 저장
    @Override
    public void save(String email, String password){
        userRepository.save(new User(email,password));
    }

    //회원 데이터 조회
    @Override
    public User find(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmailAndPassword(email, password);

        //회원 데이터가 있으면 해당 값 리턴(없으면 null 리턴)
        return userOptional.orElse(null);
    }
}
