package com.cos.security1.config.oauth;

/**
 * OAuth2UserInfo
 * <pre>
 * Describe here
 * </pre>
 *
 * @version 1.0,
 */
public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
