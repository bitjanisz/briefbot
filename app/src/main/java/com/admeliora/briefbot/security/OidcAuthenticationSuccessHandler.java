package com.admeliora.briefbot.security;

import com.admeliora.briefbot.adapter.out.persistence.user.jpa.UserRepositoryJpa;
import com.admeliora.briefbot.application.user.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Success handler for OAuth2/OIDC authentication
 * Updates last login timestamp and redirects to saved request or default location
 */
@Slf4j
public class OidcAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserRepositoryJpa userRepository;

    public OidcAuthenticationSuccessHandler(UserRepositoryJpa userRepository) {
        this.userRepository = userRepository;
        // Set default target URL if no saved request exists
        setDefaultTargetUrl("/users/me");
        setAlwaysUseDefaultTargetUrl(false); // Allow redirecting to saved request
    }

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication.getPrincipal() instanceof org.springframework.security.oauth2.core.user.OAuth2User oauth2User) {
            String sub = oauth2User.getAttribute("sub");
            userRepository.findByOidcSub(sub).ifPresentOrElse(user -> {
                user.setLastLoginAt(LocalDateTime.now());
                userRepository.save(user);
                log.info("User logged in via OAuth2: {}", user.getEmail());
            }, () -> {
                User newUser = User.builder()
                        .oidcSub(sub)
                        .givenName(oauth2User.getAttribute("given_name"))
                        .familyName(oauth2User.getAttribute("family_name"))
                        .email(oauth2User.getAttribute("email"))
                        .pictureUrl(oauth2User.getAttribute("picture"))
                        .lastLoginAt(LocalDateTime.now())
                        .build();
                userRepository.save(newUser);
                log.info("New user registered via OAuth2: {}", newUser.getEmail());
            });
        }

        // This will redirect to saved request or default target URL
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
