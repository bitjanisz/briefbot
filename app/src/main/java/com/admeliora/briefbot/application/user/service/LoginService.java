package com.admeliora.briefbot.application.user.service;

import com.admeliora.briefbot.application.user.model.User;
import com.admeliora.briefbot.application.user.port.in.LoginPort;
import com.admeliora.briefbot.application.user.port.in.command.LoginCommand;
import com.admeliora.briefbot.application.user.port.out.UserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service for user authentication with email and password
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService implements LoginPort {

    private final UserPort userPort;
//    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User login(LoginCommand command) {
        User user = userPort.findByEmail(command.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (user.getPasswordHash() == null) {
            throw new IllegalArgumentException("User registered via OAuth, please use OAuth login");
        }

//        if (!passwordEncoder.matches(command.password(), user.getPasswordHash())) {
//            throw new IllegalArgumentException("Invalid email or password");
//        }

        // Update last login
        user.setLastLoginAt(LocalDateTime.now());
        userPort.save(user);

        log.info("User logged in successfully: {}", user.getEmail());
        return user;
    }
}

