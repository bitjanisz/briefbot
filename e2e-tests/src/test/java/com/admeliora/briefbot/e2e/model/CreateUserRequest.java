package com.admeliora.briefbot.e2e.model;

import lombok.Builder;

@Builder
public record CreateUserRequest(
        String email,
        String givenName,
        String familyName,
        String password
) {
}
