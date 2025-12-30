package com.admeliora.briefbot.application.user.port.in.command;

import lombok.Builder;

/**
 * Command for user registration with email and password
 */
@Builder
public record RegisterUserCommand(
        String email,
        String givenName,
        String familyName
) {
}

