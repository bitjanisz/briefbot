package com.admeliora.briefbot.adapter.in.web.service.model.response;

import java.time.Instant;

public record ServiceResponse(Long id, String name, String description, Long accountId, Instant createdAt,
                              Instant updatedAt) {
}