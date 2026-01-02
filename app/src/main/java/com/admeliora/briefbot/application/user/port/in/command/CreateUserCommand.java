package com.admeliora.briefbot.application.user.port.in.command;

import lombok.Builder;

/**
 * Command for creating a user by admin
 */
@Builder
public record CreateUserCommand(
        String email,
        String givenName,
        String familyName
) {
}
