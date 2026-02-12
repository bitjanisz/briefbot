package com.admeliora.briefbot.application.user.service;

import com.admeliora.briefbot.application.user.model.User;
import com.admeliora.briefbot.application.user.port.in.UpdateUserPort;
import com.admeliora.briefbot.application.user.port.in.command.UpdateUserCommand;
import com.admeliora.briefbot.application.user.port.out.UserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for updating users by admin
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserPort {

    private final UserPort userPort;

    @Override
    @Transactional
    public User update(UpdateUserCommand command) {
        // Find existing user
        User existingUser = userPort.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("User with id " + command.id() + " not found"));

        // Check email uniqueness if changed
        if (!existingUser.getEmail().equals(command.email()) &&
            userPort.findByEmail(command.email()).isPresent()) {
            throw new IllegalArgumentException("User with email " + command.email() + " already exists");
        }

        // Update user
        User updatedUser = User.builder()
                .id(existingUser.getId())
                .oidcSub(existingUser.getOidcSub())
                .email(command.email())
                .passwordHash(existingUser.getPasswordHash())
                .givenName(command.givenName())
                .familyName(command.familyName())
                .pictureUrl(existingUser.getPictureUrl())
                .lastLoginAt(existingUser.getLastLoginAt())
                .createdAt(existingUser.getCreatedAt())
                .build();

        User savedUser = userPort.save(updatedUser);
        log.info("User updated successfully: {}", command.email());

        return savedUser;
    }
}
