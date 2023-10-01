package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * PrincipalDetails
 * <pre>
 * Describe here
 * </pre>
 *
 * @version 1.0,
 */

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행 시킴
// 로그인 진행이 완료가 되면 시큐리티 session을 만들어줌 (Security ContextHolder 에 저장함)
// 오브젝트 타입 => Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 됨
// User 오브젝트 타입 => UserDetails 타입 객체

// 시큐리티가 가지고 있는 Security Session => Authentication => UserDetails

public class PrincipalDetails  implements UserDetails, OAuth2User {
    @Getter
    private User user; // 콤포지션
    private Map<String, Object> attributes;

    // 일반 로그인
    public PrincipalDetails(User user) {
        super();
        this.user = user;
    }

    // OAuth 로그인
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    // 해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authCollection = new ArrayList<>();
        authCollection.add(()-> user.getRole());
        return authCollection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 우리 사이트! 1년 동안 로그인은 안한 경우 휴먼 계정으로 하기로 함
        // 현재 시간 - 로긴 시간  => 1년 초과 시 return false;
        return true;
    }

    // OAuth2User Override method
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(attributes.get("sub"));
    }
}
