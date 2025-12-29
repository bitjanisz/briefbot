package com.admeliora.briefbot.security.jwt;

import org.springframework.security.core.AuthenticationException;

/**
 * Exception thrown when JWT authentication fails
 */
public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(String message) {
        super(message);
    }

    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}

