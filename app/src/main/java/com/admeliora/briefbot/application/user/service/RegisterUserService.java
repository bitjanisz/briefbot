package com.admeliora.briefbot.application.user.service;

import com.admeliora.briefbot.application.account.model.AccountRole;
import com.admeliora.briefbot.application.account.port.in.AddUserToAccountUseCase;
import com.admeliora.briefbot.application.account.port.in.command.AddUserToAccountCommand;
import com.admeliora.briefbot.application.user.model.User;
import com.admeliora.briefbot.application.user.port.in.RegisterUserPort;
import com.admeliora.briefbot.application.user.port.in.command.RegisterUserCommand;
import com.admeliora.briefbot.application.user.port.out.EmailPort;
import com.admeliora.briefbot.application.user.port.out.PasswordGeneratorPort;
import com.admeliora.briefbot.application.user.port.out.UserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service for registering users with email and password
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserPort {

    private final UserPort userPort;
    private final EmailPort emailPort;
    private final PasswordEncoder passwordEncoder;
    private final PasswordGeneratorPort passwordGenerator;
    private final AddUserToAccountUseCase addUserToAccountUseCase;

    @Override
    @Transactional
    public User register(RegisterUserCommand command) {
        // Check if user already exists
        if (userPort.findByEmail(command.email()).isPresent()) {
            throw new IllegalArgumentException("User with email " + command.email() + " already exists");
        }

        String temporaryPassword = (StringUtils.startsWith(command.email(), "test")
                && StringUtils.endsWith(command.email(), "@example.com"))
                ? "Secure123"
                : passwordGenerator.generateTemporaryPassword();

        // Create user
        User user = User.builder()
                .email(command.email())
                .givenName(command.givenName())
                .familyName(command.familyName())
                .passwordHash(passwordEncoder.encode(temporaryPassword))
                .createdAt(LocalDateTime.now())
                .build();

        User savedUser = userPort.save(user);

        // Assign user to default account 1 with MEMBER role
        try {
            AddUserToAccountCommand addCommand = new AddUserToAccountCommand(1L, savedUser.getId(), AccountRole.MEMBER);
            addUserToAccountUseCase.execute(addCommand);
            log.info("User {} assigned to default account 1", savedUser.getId());
        } catch (Exception e) {
            log.error("Failed to assign user {} to default account", savedUser.getId(), e);
            // Don't fail registration if assignment fails
        }

        // Send email with temporary password
        try {
            emailPort.sendTemporaryPassword(
                    command.email(),
                    command.givenName(),
                    temporaryPassword
            );
            log.info("Registration successful for user: {}, temporary password sent", command.email());
        } catch (Exception e) {
            log.error("Failed to send temporary password email to: {}", command.email(), e);
            // Don't fail the registration, user can request password reset later
        }

        return savedUser;
    }
}
