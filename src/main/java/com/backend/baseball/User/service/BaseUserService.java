package com.backend.baseball.User.service;

import com.backend.baseball.User.entity.User;
import com.backend.baseball.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public void selectMyClub(String myClub,String email,String password){
        User user=userRepository.findByEmailAndPassword(email,password)
                .orElseThrow(()->new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.changeClub(myClub);
    }

    @Transactional
    @Override
    public void updateNickname(String nickname,String email,String password){
        User userExists=userRepository.findByNickname(nickname);
        if(userExists!=null){
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
        User user=userRepository.findByEmailAndPassword(email,password)
                .orElseThrow(()->new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.changeNickname(nickname);

    }
}
