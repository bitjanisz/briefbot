package com.admeliora.briefbot.adapter.in.web.ai;

import com.admeliora.briefbot.adapter.in.web.ai.model.ChatRequest;
import com.admeliora.briefbot.adapter.in.web.ai.model.ChatResponse;
import com.admeliora.briefbot.application.ai.service.AiChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ai/chat")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "AI Chat", description = "AI chat operations using Ollama with case study context")
public class AiChatRestAdapter {

    private final AiChatService aiChatService;

    @PostMapping
    @Operation(
            summary = "Send a chat message to AI",
            description = "Send a message to the AI assistant. The AI has access to case studies with services data through function calling."
    )
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        log.info("Received chat request: {}", request.message());

        try {
            String response = aiChatService.chat(request.message());
            return ResponseEntity.ok(new ChatResponse(response));
        } catch (Exception e) {
            log.error("Error processing chat request", e);
            return ResponseEntity.internalServerError()
                    .body(new ChatResponse("Error: " + e.getMessage()));
        }
    }
}

