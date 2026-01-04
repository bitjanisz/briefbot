package com.admeliora.briefbot.application.user.port.in.command;

import lombok.Builder;

/**
 * Command for user login with email and password
 */
@Builder
public record LoginCommand(
        String email,
        String password
) {
}

