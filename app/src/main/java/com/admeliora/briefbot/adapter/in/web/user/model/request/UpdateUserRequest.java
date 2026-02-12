package com.admeliora.briefbot.adapter.in.web.user.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @Email
        String email,
        @Size(max = 100)
        String givenName,
        @Size(max = 100)
        String familyName
) {
}
