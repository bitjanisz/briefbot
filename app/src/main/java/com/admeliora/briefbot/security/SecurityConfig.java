package com.admeliora.briefbot.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseCookie;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig {

    @Value("${security.jwt.secret:}")
    private String jwtSecret;

    @Value("${security.jwt.expiration:86400000}")
    private long jwtExpirationMs;

    @Value("${security.jwt.cookie.name:DEMO_JWT}")
    private String jwtCookieName;

    @Value("${security.jwt.cookie.max-age:86400}")
    private int jwtCookieMaxAge;

    @Value("${security.jwt.cookie.secure:false}")
    private boolean jwtCookieSecure;

    @Value("${security.jwt.cookie.http-only:true}")
    private boolean jwtCookieHttpOnly;

    @Value("${security.jwt.cookie.same-site:Lax}")
    private String jwtCookieSameSite;

    @Value("${security.redirect.default-success-url:/}")
    private String defaultSuccessUrl;

    @Value("${security.redirect.login-url:/login}")
    private String loginUrl;

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        try {
            http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/", "/index.html", "/other/**", "/login/**", "/assets/**", "/vite.svg", "/static/**").permitAll()
                    .requestMatchers("/api/users/me").permitAll()
                    .requestMatchers("/api/sample").permitAll()
                    .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                    .loginPage(loginUrl)
                    .successHandler(jwtCookieSuccessHandler())
                )
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/"))
                .addFilterBefore(jwtAuthFilter, AnonymousAuthenticationFilter.class);
            return http.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public AuthenticationSuccessHandler jwtCookieSuccessHandler() {
        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
            OAuth2User user = (OAuth2User) authentication.getPrincipal();
            String subject = user != null ? user.getName() : "unknown";
            Map<String, Object> claims = new HashMap<>();
            if (user != null) {
                claims.put("email", user.getAttribute("email"));
                claims.put("name", user.getAttribute("name"));
            }
            JwtUtil jwtUtil = new JwtUtil(jwtSecret, Duration.ofMillis(jwtExpirationMs));
            String token = jwtUtil.createToken(subject, claims);

            ResponseCookie cookie = ResponseCookie.from(jwtCookieName, token)
                .httpOnly(jwtCookieHttpOnly)
                .secure(jwtCookieSecure)
                .path("/")
                .maxAge(Duration.ofSeconds(jwtCookieMaxAge))
                .sameSite(jwtCookieSameSite)
                .build();
            response.addHeader("Set-Cookie", cookie.toString());

            try {
                response.sendRedirect(defaultSuccessUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
