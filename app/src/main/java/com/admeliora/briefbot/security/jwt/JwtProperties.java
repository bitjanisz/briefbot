package com.admeliora.briefbot.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@RequiredArgsConstructor
@Getter
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {
    private final String secret;
    private final long expiration;
    private final String issuer = "briefbot";
    private final String audience = "briefbot-api";
    private final Cookie cookie = new Cookie();

    @Getter
    @AllArgsConstructor
    public static class Cookie {
        private final String name = "BRIEFBOT_JWT";
        private final int maxAge = 86400;
        private final boolean secure = false;
        private final boolean httpOnly = true;
        private final String sameSite = "Lax";
    }
}

