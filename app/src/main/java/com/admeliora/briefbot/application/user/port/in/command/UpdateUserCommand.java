package com.admeliora.briefbot.application.user.port.in.command;

import lombok.Builder;

/**
 * Command for updating a user by admin
 */
@Builder
public record UpdateUserCommand(
        Long id,
        String email,
        String givenName,
        String familyName
) {
}
