package com.admeliora.briefbot.e2e.model;

import lombok.Builder;

@Builder
public record UpdateUserRequest(
        String email,
        String givenName,
        String familyName,
        String password
) {
}
