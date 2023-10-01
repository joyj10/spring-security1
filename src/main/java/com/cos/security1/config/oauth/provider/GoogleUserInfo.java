package com.cos.security1.config.oauth.provider;

import com.cos.security1.config.oauth.OAuth2UserInfo;

import java.util.Map;

/**
 * GoogleUserInfo
 * <pre>
 * Describe here
 * </pre>
 *
 * @version 1.0,
 */
public class GoogleUserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes; // oauth2User.getAttributes()

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("sub"));
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getEmail() {
        return String.valueOf(attributes.get("email"));
    }

    @Override
    public String getName() {
        return String.valueOf(attributes.get("name"));
    }
}
