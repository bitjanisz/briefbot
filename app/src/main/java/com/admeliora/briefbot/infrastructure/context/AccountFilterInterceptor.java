package com.admeliora.briefbot.infrastructure.context;

import com.admeliora.briefbot.application.account.port.out.UserAccountPort;
import com.admeliora.briefbot.application.user.port.out.UserPort;
import com.admeliora.briefbot.security.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountFilterInterceptor extends OncePerRequestFilter {

    private final AccountFilterContext accountFilterContext;
    private final UserAccountPort userAccountPort;
    private final UserPort userPort;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String email;
            Long userId = null;
            Long accountId = null;

            // Check if JWT authentication with CustomUserDetails
            if (authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {
                email = customUserDetails.getEmail();
                userId = customUserDetails.getUserId();
                accountId = customUserDetails.getAccountId();

                accountFilterContext.setAccountId(accountId);
                accountFilterContext.setUserId(userId);
                accountFilterContext.setUserEmail(email);
                log.debug("JWT auth - Set context from CustomUserDetails: userId={}, accountId={}, email={}",
                        userId, accountId, email);
                filterChain.doFilter(request, response);
                return;
            }

            // OAuth2 authentication
            if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
                email = oidcUser.getEmail();
            }
            // Form login with standard User
            else if (authentication instanceof User user) {
                email = user.getUsername();
            }
            // UsernamePasswordAuthenticationToken with String principal
            else if (authentication instanceof UsernamePasswordAuthenticationToken
                    && authentication.getPrincipal() instanceof String principalStr) {
                email = principalStr;
            } else {
                filterChain.doFilter(request, response);
                return;
            }

            // For non-JWT auth, lookup account from database
            var userAccount = userAccountPort.findPrimaryAccountIdByUserEmail(email);

            userAccount.ifPresentOrElse(au -> {
                accountFilterContext.setAccountId(au.getAccountId());
                accountFilterContext.setUserId(au.getUserId());
                accountFilterContext.setUserEmail(email);
                log.debug("Non-JWT auth - Set accountId filter to: {} for user: {}", au.getAccountId(), email);
            }, () -> {
                accountFilterContext.setUserEmail(email);
                userPort.findByEmail(email).ifPresent(user ->
                        accountFilterContext.setUserId(user.getId())
                );
                log.warn("No account found for user: {}", email);
            });
        }

        filterChain.doFilter(request, response);
    }
}

