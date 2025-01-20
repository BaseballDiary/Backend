package com.backend.baseball.security.auth;

import com.backend.baseball.domain.dto.UserDto;
import com.backend.baseball.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.backend.baseball.domain.entity.User;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final HttpSession session;
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user=userRepository.findById(id).orElseThrow(()->
                new UsernameNotFoundException("해당 사용자가 존재하지 않습니다."+id));
        session.setAttribute("user", new UserDto.Response(user));

        /*시큐리티 세션에 유저 정보 저장*/
        return new CustomUserDetails(user);
    }
}
