package com.admeliora.briefbot.application.user.service;

import com.admeliora.briefbot.application.user.model.User;
import com.admeliora.briefbot.application.user.port.in.CreateUserPort;
import com.admeliora.briefbot.application.user.port.in.command.CreateUserCommand;
import com.admeliora.briefbot.application.user.port.out.EmailPort;
import com.admeliora.briefbot.application.user.port.out.PasswordGeneratorPort;
import com.admeliora.briefbot.application.user.port.out.UserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service for creating users by admin
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CreateUserService implements CreateUserPort {

    private final UserPort userPort;
    private final EmailPort emailPort;
    private final PasswordEncoder passwordEncoder;
    private final PasswordGeneratorPort passwordGenerator;

    @Override
    @Transactional
    public User create(CreateUserCommand command) {
        // Check if user already exists
        if (userPort.findByEmail(command.email()).isPresent()) {
            throw new IllegalArgumentException("User with email " + command.email() + " already exists");
        }

        String temporaryPassword = passwordGenerator.generateTemporaryPassword();

        // Create user
        User user = User.builder()
                .email(command.email())
                .givenName(command.givenName())
                .familyName(command.familyName())
                .passwordHash(passwordEncoder.encode(temporaryPassword))
                .createdAt(LocalDateTime.now())
                .build();

        User savedUser = userPort.save(user);

        // Send email with temporary password
        try {
            emailPort.sendTemporaryPassword(
                    command.email(),
                    command.givenName(),
                    temporaryPassword
            );
            log.info("User created successfully for: {}, temporary password sent", command.email());
        } catch (Exception e) {
            log.error("Failed to send temporary password email to: {}", command.email(), e);
            // Don't fail the creation, user can request password reset later
        }

        return savedUser;
    }
}
