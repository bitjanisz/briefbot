package com.admeliora.briefbot.infrastructure.context;

import com.admeliora.briefbot.application.account.port.out.UserAccountPort;
import com.admeliora.briefbot.application.user.port.out.UserPort;
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
            if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
                email = oidcUser.getEmail();
            } else if (authentication instanceof User user) {
                email = user.getUsername();
            } else if (authentication instanceof UsernamePasswordAuthenticationToken
                    && authentication.getPrincipal() instanceof String principalStr) {
                email = principalStr;
            } else {
                filterChain.doFilter(request, response);
                return;
            }
            var userAccount = userAccountPort.findPrimaryAccountIdByUserEmail(email);

            userAccount.ifPresentOrElse(au -> {
                Long accountId = au.getAccountId();
                accountFilterContext.setAccountId(accountId);
                accountFilterContext.setUserId(au.getUserId());
                accountFilterContext.setUserEmail(email);
                log.debug("Set accountId filter to: {} for user: {}", accountId, email);
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

