package com.admeliora.briefbot.security;

import com.admeliora.briefbot.application.user.port.out.UserPort;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Success handler for form-based authentication
 * Updates last login timestamp and redirects to saved request or default location
 */
@Slf4j
@Component
public class FormAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserPort userPort;

    public FormAuthenticationSuccessHandler(UserPort userPort) {
        this.userPort = userPort;
        // Set default target URL if no saved request exists
        setDefaultTargetUrl("/users/me");
        setAlwaysUseDefaultTargetUrl(false); // Allow redirecting to saved request
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                       HttpServletResponse response,
                                       Authentication authentication) throws IOException, ServletException {
        String email = authentication.getName();

        userPort.findByEmail(email).ifPresent(user -> {
            user.setLastLoginAt(LocalDateTime.now());
            userPort.save(user);
            log.info("User logged in via form: {}", email);
        });

        // This will redirect to saved request or default target URL
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

