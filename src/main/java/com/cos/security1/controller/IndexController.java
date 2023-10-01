package com.cos.security1.controller;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * IndexController
 * <pre>
 * Describe here
 * </pre>
 *
 * @version 1.0,
 */

@Slf4j
@Controller // View Return
@RequiredArgsConstructor
public class IndexController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"","/"})
    public String index() {
        // 머스테치 기본 폴더 src/main/resources/
        // viewResolver 설정 : templates(prefix), .mustache(suffix) 생략 가능!
        return "index"; // src/main/resources/templates/index.mustache
    }

    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principal) {
        log.info("### principal = {} ", principal);
        log.info("### principal = {} ", principal.getUser().getProvider());
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    // login path 스프링 시큐리티 해당 주소 낚아챔 - SecurityConfig 파일 세팅 후 낚아채는 현상 사라짐
    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        user.setRole("ROLE_USER");
        user.setPassword(encodePassword(user.getPassword()));
        userRepository.save(user); // 회원 가입은 잘됨, but, 비밀번호 암호화 되지 않아 시큐리티 로그인 할 수 없음
        return "redirect:/loginForm";
    }

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication,
                                          @AuthenticationPrincipal UserDetails userDetails,
                                          @AuthenticationPrincipal PrincipalDetails principalDetails2) { // DI (의존성 주입
        log.info("/test/login===============");

        // PrincipalDetails를 받을 수 있느 방법은 2가지, UserDetails 상속했기 떄문에 파라미터로도 받을 수 있음
        PrincipalDetails principalDetails1 = (PrincipalDetails) authentication.getPrincipal();
        log.info("### principalDetails1 = {} ", principalDetails1.getUser());
        log.info("### principalDetails2 = {} ", principalDetails2.getUser());

        log.info("### userDetails = {} ", userDetails.getUsername());
        return "세션 정보 확인";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOauthLogin(Authentication authentication,
                                               @AuthenticationPrincipal OAuth2User oAuth2User2) { // DI (의존성 주입
        log.info("/test/login===============");

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("### oAuth2User = {} ", oAuth2User.getAttributes());
        log.info("### oAuth2User2 = {} ", oAuth2User2.getAttributes());

        return "oauth 세션 정보 확인";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "data";
    }


    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }


}
