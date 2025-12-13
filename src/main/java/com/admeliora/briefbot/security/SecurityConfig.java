package com.admeliora.briefbot.security;

import com.admeliora.briefbot.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            OidcAuthenticationSuccessHandler successHandler) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/health").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(o -> o.successHandler(successHandler));
        return http.build();
    }

    @Bean
    OidcAuthenticationSuccessHandler customAuthenticationSuccessHandler(UserRepository userRepository) {
        return new OidcAuthenticationSuccessHandler(userRepository);
    }
}