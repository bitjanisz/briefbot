package com.admeliora.briefbot.application.user.port.in;

import com.admeliora.briefbot.application.user.model.User;
import org.springframework.security.core.Authentication;

import java.util.Optional;

/**
 * Use case for getting the currently logged-in user
 * Supports both OAuth2 and form-based authentication
 */
public interface GetLoggedUserUseCase {
    Optional<User> execute(Authentication authentication);
}

