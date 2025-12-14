package com.admeliora.briefbot.security;

import com.admeliora.briefbot.infrastructure.adapter.out.persistence.user.jpa.UserRepositoryJpa;
import com.admeliora.briefbot.user.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
public class OidcAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepositoryJpa userRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication.getPrincipal() instanceof org.springframework.security.oauth2.core.user.OAuth2User oauth2User) {
            String sub = oauth2User.getAttribute("sub");
            userRepository.findByOidcSub(sub).ifPresentOrElse(user -> {
                user.setLastLogin(LocalDateTime.now());
                userRepository.save(user);
                log.info("User logged in: {}", user.getEmail());
            }, () -> {
                User newUser = User.builder()
                        .oidcSub(sub)
                        .givenName(oauth2User.getAttribute("given_name"))
                        .familyName(oauth2User.getAttribute("family_name"))
                        .email(oauth2User.getAttribute("email"))
                        .picture(oauth2User.getAttribute("picture"))
                        .lastLogin(LocalDateTime.now())
                        .build();
                userRepository.save(newUser);
                log.info("New user registered: {}", newUser.getEmail());
            });
        }
    }
}
