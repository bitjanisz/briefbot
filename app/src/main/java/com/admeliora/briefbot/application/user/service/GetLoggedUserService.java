package com.admeliora.briefbot.application.user.service;

import com.admeliora.briefbot.application.user.model.User;
import com.admeliora.briefbot.application.user.port.out.UserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service for getting the currently logged-in user
 * Supports both OAuth2 (OidcUser) and form-based (UserDetails) authentication
 */
@Service
@RequiredArgsConstructor
public class GetLoggedUserService implements com.admeliora.briefbot.application.user.port.in.GetLoggedUserUseCase {

    private final UserPort userPort;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> execute(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        // Check if OAuth2 login (Google)
        if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
            String sub = oidcUser.getAttribute("sub");
            if (sub == null) return Optional.empty();
            return userPort.findByOidcSub(sub);
        }

        // Check if form-based login (email/password)
        if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User userDetails) {
            String email = userDetails.getUsername();
            return userPort.findByEmail(email);
        }

        // Fallback: try to get email from authentication name
        String email = authentication.getName();
        if (email != null && !email.isEmpty()) {
            return userPort.findByEmail(email);
        }

        return Optional.empty();
    }
}

