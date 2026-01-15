package com.admeliora.briefbot.application.ai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiChatService {

    private final OllamaChatModel chatModel;

    public String chat(String userMessage) {
        try {
            log.info("Sending message to Ollama: {}", userMessage);

            ChatClient chatClient = ChatClient.builder(chatModel)
                    .defaultOptions(OllamaOptions.builder()
                            .withFunction("getCaseStudiesWithServices")
                            .build())
                    .build();

            String response = chatClient.prompt()
                    .user(userMessage)
                    .call()
                    .content();

            log.info("Received response from Ollama");
            return response;
        } catch (Exception e) {
            log.error("Error calling Ollama", e);
            throw new RuntimeException("Failed to get AI response: " + e.getMessage(), e);
        }
    }

    public ChatResponse chatWithFullResponse(String userMessage) {
        try {
            log.info("Sending message to Ollama for full response: {}", userMessage);

            Prompt prompt = new Prompt(userMessage,
                    OllamaOptions.builder()
                            .withFunction("getCaseStudiesWithServices")
                            .build());

            ChatResponse response = chatModel.call(prompt);

            log.info("Received full response from Ollama");
            return response;
        } catch (Exception e) {
            log.error("Error calling Ollama", e);
            throw new RuntimeException("Failed to get AI response: " + e.getMessage(), e);
        }
    }
}

