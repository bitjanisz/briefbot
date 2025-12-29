package com.admeliora.briefbot.security.jwt;

import com.admeliora.briefbot.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Filter to authenticate requests using JWT tokens
 * Extracts token from Authorization header or cookie and validates it
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${security.jwt.cookie.name:BRIEFBOT_JWT}")
    private String cookieName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = extractTokenFromRequest(request);

            if (token != null && StringUtils.hasText(token)) {
                Claims claims = jwtTokenProvider.validateToken(token);

                String email = claims.getSubject();
                Long userId = claims.get("userId", Long.class);
                Long accountId = claims.get("accountId", Long.class);
                String givenName = claims.get("givenName", String.class);
                String familyName = claims.get("familyName", String.class);

                // Create CustomUserDetails with JWT claims
                CustomUserDetails userDetails = new CustomUserDetails(
                        email,
                        "", // No password needed for JWT auth
                        true,
                        true,
                        true,
                        true,
                        Collections.emptyList(),
                        userId,
                        accountId,
                        email,
                        givenName,
                        familyName
                );

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.debug("JWT authentication successful for: {} (userId: {}, accountId: {})",
                        email, userId, accountId);
            }

        } catch (JwtAuthenticationException e) {
            log.error("JWT authentication failed: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Invalid or expired JWT token\"}");
            response.setContentType("application/json");
            return;
        } catch (Exception e) {
            log.error("Error during JWT authentication: {}", e.getMessage(), e);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extract JWT token from cookie or Authorization header
     * Priority: 1. Cookie, 2. Authorization header
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        // Try to get token from cookie first
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    log.debug("JWT token found in cookie: {}", cookieName);
                    return cookie.getValue();
                }
            }
        }

        // Fallback to Authorization header
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            log.debug("JWT token found in Authorization header");
            return bearerToken.substring(7);
        }

        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // Skip JWT filter for public endpoints
        return path.startsWith("/api/auth/")
                || path.startsWith("/login")
                || path.startsWith("/oauth2/")
                || path.startsWith("/h2-console")
                || path.startsWith("/actuator");
    }
}

