package com.admeliora.briefbot.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final Key jwtKey;
    private final String cookieName;

    public JwtAuthFilter(
            @Value("${security.jwt.secret}") String base64Secret,
            @Value("${security.jwt.cookie.name:DEMO_JWT}") String cookieName
    ) {
        byte[] keyBytes = Base64.getDecoder().decode(base64Secret.getBytes(StandardCharsets.UTF_8));
        this.jwtKey = Keys.hmacShaKeyFor(keyBytes);
        this.cookieName = cookieName;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        // Only filter /api/sample/**
        return !(path.startsWith("/api/sample/") || "/api/sample".equals(path));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = readCookie(request, cookieName);
        if (token == null || token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            // If parsing succeeds, expiration was valid; continue
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            // invalid signature or expired token
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private String readCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie c : cookies) {
            if (name.equals(c.getName())) return c.getValue();
        }
        return null;
    }
}

