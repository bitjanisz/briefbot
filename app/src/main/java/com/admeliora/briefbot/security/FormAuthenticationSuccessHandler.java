package com.admeliora.briefbot.security;

import com.admeliora.briefbot.adapter.out.persistence.account.jpa.UserAccountRepositoryJpa;
import com.admeliora.briefbot.application.account.model.UserAccount;
import com.admeliora.briefbot.application.user.port.out.UserPort;
import com.admeliora.briefbot.security.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Success handler for form-based authentication
 * Generates JWT token, stores it in cookie, and redirects to saved request or default URL
 */
@Slf4j
@Component
public class FormAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

//    private final UserPort userPort;
//    private final UserAccountRepositoryJpa userAccountRepository;
//    private final JwtTokenProvider jwtTokenProvider;
//    private final ObjectMapper objectMapper;
//
//    @Value("${security.jwt.cookie.name}")
//    private String cookieName;
//
//    @Value("${security.jwt.cookie.max-age}")
//    private int cookieMaxAge;
//
//    @Value("${security.jwt.cookie.secure}")
//    private boolean cookieSecure;
//
//    @Value("${security.jwt.cookie.http-only}")
//    private boolean cookieHttpOnly;
//
//    @Value("${security.jwt.cookie.same-site}")
//    private String cookieSameSite;
//
//    @Value("${security.redirect.default-success-url}")
//    private String defaultSuccessUrl;
//
//    public FormAuthenticationSuccessHandler(UserPort userPort,
//                                           UserAccountRepositoryJpa userAccountRepository,
//                                           JwtTokenProvider jwtTokenProvider,
//                                           ObjectMapper objectMapper) {
//        this.userPort = userPort;
//        this.userAccountRepository = userAccountRepository;
//        this.jwtTokenProvider = jwtTokenProvider;
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                       HttpServletResponse response,
//                                       Authentication authentication) throws IOException, ServletException {
//        String email = authentication.getName();
//
//        var user = userPort.findByEmail(email).orElseThrow(() ->
//            new RuntimeException("User not found: " + email));
//
//        user.setLastLoginAt(LocalDateTime.now());
//        userPort.save(user);
//        log.info("User logged in via form: {}", email);
//
//        // Get user's account
//        Long accountId = userAccountRepository.findByUserEmail(email)
//                .stream()
//                .findFirst()
//                .map(UserAccount::getAccountId)
//                .orElse(null);
//
//        // Generate JWT token with all user details
//        String jwtToken = jwtTokenProvider.createToken(
//                email,
//                user.getId(),
//                accountId,
//                user.getGivenName(),
//                user.getFamilyName(),
//                "form"
//        );
//
//        // Store JWT token in HTTP-only cookie
//        Cookie jwtCookie = new Cookie(cookieName, jwtToken);
//        jwtCookie.setHttpOnly(cookieHttpOnly);
//        jwtCookie.setSecure(cookieSecure);
//        jwtCookie.setPath("/");
//        jwtCookie.setMaxAge(cookieMaxAge);
//        jwtCookie.setAttribute("SameSite", cookieSameSite);
//        response.addCookie(jwtCookie);
//
//        log.info("JWT token generated and stored in cookie for form login user: {}", email);
//
//        // Set default target URL before calling super
//        setDefaultTargetUrl(defaultSuccessUrl);
//        setAlwaysUseDefaultTargetUrl(false);
//
//        // Redirect to saved request or default URL
//        super.onAuthenticationSuccess(request, response, authentication);
//    }
}

