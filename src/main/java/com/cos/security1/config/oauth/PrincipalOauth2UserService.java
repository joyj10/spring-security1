package com.cos.security1.config.oauth;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    // 구글로 부터 받은 userRequest 데이터에 대해 후처리 하는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("### userRequest = {} ", userRequest);
        log.info("### getAccessToken = {} ", userRequest.getAccessToken().getTokenValue());
        log.info("### getAdditionalParameters = {} ", userRequest.getAdditionalParameters());
        log.info("### getClientRegistration = {} ", userRequest.getClientRegistration());
        log.info("### getClientId = {} ", userRequest.getClientRegistration().getClientId());
        log.info("### getClientName = {} ", userRequest.getClientRegistration().getClientName());
        log.info("### super.loadUser(userRequest) = {} ", super.loadUser(userRequest).getAttributes());
        return super.loadUser(userRequest);
    }
}
