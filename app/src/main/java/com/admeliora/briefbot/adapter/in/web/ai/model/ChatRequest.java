package com.admeliora.briefbot.adapter.in.web.ai.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Chat request to send a message to the AI assistant")
public record ChatRequest(
        @NotBlank(message = "Message cannot be blank")
        @Size(max = 10000, message = "Message cannot exceed 10000 characters")
        @Schema(description = "User message to send to the AI", example = "Tell me about the available case studies with their services")
        String message
) {
}
