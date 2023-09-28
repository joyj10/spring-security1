package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * IndexController
 * <pre>
 * Describe here
 * </pre>
 *
 * @version 1.0,
 */

@Controller // View Return
public class IndexController {
    @GetMapping({"","/"})
    public String index() {
        // 머스테치 기본 폴더 src/main/resources/
        // viewResolver 설정 : templates(prefix), .mustache(suffix) 생략 가능!
        return "index"; // src/main/resources/templates/index.mustache
    }

    @GetMapping("/user")
    public String user() {
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

    // 스프링 시큐리티 해당 주소 낚아챔 - SecurityConfig 파일 세팅 후 낚아채는 현상 사라짐
    @GetMapping("/login")
    public @ResponseBody String login() {
        return "login";
    }

    @GetMapping("/join")
    public @ResponseBody String join() {
        return "join";
    }

    @GetMapping("/joinProc")
    public @ResponseBody String joinProc() {
        return "회원가입 완료됨!";
    }

}
