package com.backend.baseball.security.auth;

import com.backend.baseball.Login.repository.UserRepository;
import com.backend.baseball.Login.entity.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.backend.baseball.Login.dto.UserDto;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final HttpSession session;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(email).orElseThrow(()->
                new UsernameNotFoundException("해당 사용자가 존재하지 않습니다. "+email));
        session.setAttribute("user", user);


        /*시큐리티 세션에 유저 정보 저장*/
        return new CustomUserDetails(user);
    }
}
