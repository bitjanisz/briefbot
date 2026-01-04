package com.admeliora.briefbot.security.jwt;

import com.admeliora.briefbot.security.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
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
    private final ObjectMapper objectMapper;
    private final JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            final String token = extractTokenFromRequest(request);
            if (StringUtils.hasText(token)) {
                Claims claims = jwtTokenProvider.validateToken(token);
                CustomUserDetails userDetails = createUserDetails(claims);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtAuthenticationException e) {
            log.error("JWT authentication failed: {}", e.getMessage());
            handleAuthenticationError(response, HttpStatus.UNAUTHORIZED, "Invalid or expired JWT token", e.getMessage());
            return;
        } catch (Exception e) {
            log.error("Error during JWT authentication", e);
            handleAuthenticationError(response, HttpStatus.INTERNAL_SERVER_ERROR,
                    "Internal server error during JWT authentication", e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }

    private CustomUserDetails createUserDetails(Claims claims) {
        return new CustomUserDetails(
                claims.getSubject(),
                "",
                true, true, true, true,
                Collections.emptyList(),
                claims.get("userId", Long.class),
                claims.get("accountId", Long.class),
                claims.getSubject(),
                claims.get("givenName", String.class),
                claims.get("familyName", String.class)
        );
    }

    private void handleAuthenticationError(HttpServletResponse response, HttpStatus status,
                                           String title, String detail) throws IOException {
        SecurityContextHolder.clearContext();
        response.setStatus(status.value());
        response.setContentType("application/problem+json");
        ProblemDetail problem = ProblemDetail.forStatus(status);
        problem.setTitle(title);
        problem.setDetail(detail);
        objectMapper.writeValue(response.getWriter(), problem);
    }

    /**
     * Extract JWT token from cookie or Authorization header
     * Priority: 1. Cookie, 2. Authorization header
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (jwtProperties.getCookie().getName().equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        final String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        final String path = request.getRequestURI();
        return path.startsWith("/api/auth/") ||
                path.startsWith("/login") ||
                path.startsWith("/oauth2/") ||
                path.startsWith("/h2-console") ||
                path.startsWith("/actuator");
    }
}
