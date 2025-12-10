package com.admeliora.briefbot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // disable if using JWT
            .sessionManagement(sm -> sm.sessionCreationPolicy(
                org.springframework.security.config.http.SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login", "/auth/register", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .anyRequest().authenticated())
            .formLogin(form -> form.disable())     // disable default login page
            .httpBasic(basic -> basic.disable());  // disable basic auth

        return http.build();
    }
}