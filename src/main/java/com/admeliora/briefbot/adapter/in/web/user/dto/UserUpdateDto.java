package com.admeliora.briefbot.adapter.in.web.user.dto;

import jakarta.validation.constraints.Size;

public record UserUpdateDto(
    @Size(max = 100)
    String givenName,
    @Size(max = 100)
    String familyName,
    @Size(max = 512)
    String picture
) {}
