package com.admeliora.briefbot.security;

import com.admeliora.briefbot.adapter.out.persistence.account.jpa.UserAccountRepositoryJpa;
import com.admeliora.briefbot.adapter.out.persistence.user.jpa.UserRepositoryJpa;
import com.admeliora.briefbot.security.jwt.JwtAuthenticationFilter;
import com.admeliora.briefbot.security.jwt.JwtProperties;
import com.admeliora.briefbot.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${security.redirect.login-url:/login}")
    private String loginUrl;

//    @Bean
//    @Order(1)
//    SecurityFilterChain managementSecurityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .securityMatcher(request -> request.getLocalPort() == 8081)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/h2-console/**",
//                                "/actuator/**"
//                        ).permitAll()
//                        .anyRequest().denyAll()
//                )
//                .csrf(AbstractHttpConfigurer::disable)
//                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
//                .exceptionHandling(exception -> exception
//                        .defaultAuthenticationEntryPointFor(
//                                (request, response, authException) -> response.sendError(403, "Forbidden"),
//                                request -> true
//                        )
//                );
//        return http.build();
//    }

    @Bean
//    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           OidcAuthenticationSuccessHandler oidcSuccessHandler,
                                           JwtAuthenticationFilter jwtAuthenticationFilter,
                                           JwtProperties jwtProperties) {
        try {
            http
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/", "/index.html", "/login/**", "/assets/**", "/vite.svg", "/static/**").permitAll()
                            .requestMatchers("/api/users/**").permitAll()
                            .requestMatchers("/api/auth/**").permitAll()
                            .requestMatchers("/api/sample").permitAll()
                            .anyRequest().authenticated()
                    )
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless for JWT
                    )
                    .headers(headers -> headers
                            .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                    )
                    .oauth2Login(oauth -> oauth
                            .loginPage(loginUrl)
                            .successHandler(oidcSuccessHandler)
                    )
                    .logout(logout -> logout
                            .logoutUrl("/logout")
                            .invalidateHttpSession(true)
                            .clearAuthentication(true)
                            .deleteCookies(jwtProperties.getCookie().getName())
                            .permitAll()
                    )
//                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/"))
                    .exceptionHandling(exception -> exception
                            .defaultAuthenticationEntryPointFor(
                                    (request, response, authException) -> response.sendError(403, "Forbidden"),
                                    request -> true
                            )
                    )
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//                    .addFilterBefore(jwtAuthFilter, AnonymousAuthenticationFilter.class);

            http.cors(cors -> {});

            return http.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    @ConditionalOnProperty(name = "spring.security.oauth2.client.registration.google.client-id")
    LogoutSuccessHandler oidcLogoutSuccessHandler(ClientRegistrationRepository clientRegistrationRepository) {
        OidcClientInitiatedLogoutSuccessHandler successHandler =
                new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);

        successHandler.setPostLogoutRedirectUri("{baseUrl}/");

        return successHandler;
    }

    @Bean
    OidcAuthenticationSuccessHandler customAuthenticationSuccessHandler(
            UserRepositoryJpa userRepository,
            UserAccountRepositoryJpa userAccountRepository,
            JwtTokenProvider jwtTokenProvider,
            JwtProperties jwtProperties,
            @Value("${security.redirect.default-success-url}") String defaultSuccessUrl
    ) {
        return new OidcAuthenticationSuccessHandler(userRepository, userAccountRepository, jwtTokenProvider, jwtProperties, defaultSuccessUrl);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
