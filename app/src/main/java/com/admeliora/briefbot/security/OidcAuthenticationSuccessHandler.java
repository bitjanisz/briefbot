package com.admeliora.briefbot.security;

import com.admeliora.briefbot.adapter.out.persistence.account.jpa.UserAccountRepositoryJpa;
import com.admeliora.briefbot.adapter.out.persistence.user.jpa.UserRepositoryJpa;
import com.admeliora.briefbot.application.account.model.UserAccount;
import com.admeliora.briefbot.application.user.model.User;
import com.admeliora.briefbot.security.jwt.JwtProperties;
import com.admeliora.briefbot.security.jwt.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Success handler for OAuth2/OIDC authentication
 * Generates JWT token, stores it in cookie, and redirects to saved request or default URL
 */
@Slf4j
public class OidcAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserRepositoryJpa userRepository;
    private final UserAccountRepositoryJpa userAccountRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final String defaultSuccessUrl;

    public OidcAuthenticationSuccessHandler(UserRepositoryJpa userRepository,
                                            UserAccountRepositoryJpa userAccountRepository,
                                            JwtTokenProvider jwtTokenProvider,
                                            JwtProperties jwtProperties,
                                            @Value("${security.redirect.default-success-url}") String defaultSuccessUrl) {
        this.userRepository = userRepository;
        this.userAccountRepository = userAccountRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtProperties = jwtProperties;
        this.defaultSuccessUrl = defaultSuccessUrl;
    }

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        if (authentication.getPrincipal() instanceof org.springframework.security.oauth2.core.user.OAuth2User oauth2User) {
            String sub = oauth2User.getAttribute("sub");
            String email = oauth2User.getAttribute("email");

            User user = userRepository.findByOidcSub(sub).map(existingUser -> {
                existingUser.setLastLoginAt(LocalDateTime.now());
                userRepository.save(existingUser);
                log.info("User logged in via OAuth2: {}", existingUser.getEmail());
                return existingUser;
            }).orElseGet(() -> {
                User newUser = User.builder()
                        .oidcSub(sub)
                        .givenName(oauth2User.getAttribute("given_name"))
                        .familyName(oauth2User.getAttribute("family_name"))
                        .email(email)
                        .pictureUrl(oauth2User.getAttribute("picture"))
                        .lastLoginAt(LocalDateTime.now())
                        .build();
                User savedUser = userRepository.save(newUser);
                log.info("New user registered via OAuth2: {}", newUser.getEmail());
                return savedUser;
            });

            // Get user's account
            Long accountId = userAccountRepository.findByUserEmail(email)
                    .stream()
                    .findFirst()
                    .map(UserAccount::getAccountId)
                    .orElse(null);

            // Generate JWT token with all user details
            String jwtToken = jwtTokenProvider.createToken(
                    email,
                    user.getId(),
                    accountId,
                    user.getGivenName(),
                    user.getFamilyName(),
                    "oauth2"
            );

            var jwtCookieConfig = jwtProperties.getCookie();

            // Store JWT token in HTTP-only cookie
            Cookie jwtCookie = new Cookie(jwtCookieConfig.getName(), jwtToken);
            jwtCookie.setHttpOnly(jwtCookieConfig.isHttpOnly());
            jwtCookie.setSecure(jwtCookieConfig.isSecure());
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(jwtCookieConfig.getMaxAge());
            jwtCookie.setAttribute("SameSite", jwtCookieConfig.getSameSite());
            response.addCookie(jwtCookie);

            log.info("JWT token generated and stored in cookie for OAuth2 user: {}", email);

            // Set default target URL before calling super
            setDefaultTargetUrl(defaultSuccessUrl);
            setAlwaysUseDefaultTargetUrl(false);

            // Redirect to saved request or default URL
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
