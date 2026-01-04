package com.admeliora.briefbot.adapter.in.web.user.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @Size(max = 100)
        String givenName,
        @Size(max = 100)
        String familyName,
        @Size(max = 512)
        String picture,
        @Email
        String email
) {
}