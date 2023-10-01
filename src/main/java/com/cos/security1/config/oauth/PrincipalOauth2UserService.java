package com.cos.security1.config.oauth;


import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    // 구글로 부터 받은 userRequest 데이터에 대해 후처리 하는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 구글 로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인을 완료 -> code 리턴(OAuth-Client 라이브러리) -> AccessToken 요청
        // userReqeust 정보 -> loadUser 함수 -> 회원 프로필을 구글로 부터 받아줌
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getClientId(); // google
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider + "_" + providerId; // google_10974283122344932
        String email = oAuth2User.getAttribute("email");
        String role = "ROLE_USER";

        Optional<User> opt = userRepository.findByUsername(username);
        User userEntity = null;

        if (opt.isPresent()) {
            log.info("구글 로그인을 이미 한 적이 있습니다.");

        } else {
            log.info("구글 로그인이 최초입니다.");
            userEntity = User.builder()
                    .username(username)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }

        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
