package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 시큐리티 설정에서 loginProcessingUrl("/login");
 * login 요청이 오면 자동으로 UserDetailService 타입으로 IoC 되어 있는 loadUserByUsername 함수가 실행 됨 (정해진 규칙임)
 */
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Security Session(내부 Authentication(내부 UserDetails))
    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어짐
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = userRepository.findByUsername(username);
        if (opt.isPresent()) {
            return new PrincipalDetails(opt.get());
        }
        return null;
    }
}
