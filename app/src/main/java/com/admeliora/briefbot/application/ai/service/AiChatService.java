package com.admeliora.briefbot.application.ai.service;

import com.admeliora.briefbot.adapter.in.web.ai.model.ChatResponse;
import com.admeliora.briefbot.application.ai.model.CaseStudyWithServicesDto;
import com.admeliora.briefbot.application.ai.port.out.CaseStudyWithServicesPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiChatService {

    private final OllamaChatModel chatModel;
    private final CaseStudyWithServicesPort caseStudyWithServicesPort;

    public ChatResponse chat(String userMessage) {
        try {
            log.info("Sending message to Ollama: {}", userMessage);

            // Get all case studies with services for context
            List<CaseStudyWithServicesDto> caseStudies = caseStudyWithServicesPort.getAllCaseStudiesWithServices();

            // Build context from case studies
            String context = buildCaseStudyContext(caseStudies);

            // Create system prompt template
            String template = """
                    You are a helpful assistant for a business services company.
                    Analyze the customer's request and suggest relevant services based on our case studies.
                    IMPORTANT: Detect the language of the user's message and respond in the SAME language.
                    Use the provided case study context to make informed suggestions.
                    
                    Available Services:
                    {services}
                    """;

            PromptTemplate promptTemplate = new PromptTemplate(template);
            promptTemplate.add("services", context);

            // Create prompt with messages
            List<org.springframework.ai.chat.messages.Message> messages = List.of(
                new SystemMessage(promptTemplate.render()),
                new UserMessage(userMessage)
            );
            Prompt prompt = new Prompt(messages, OllamaOptions.builder().build());

            var chatResponse = chatModel.call(prompt);
            String aiResponse = chatResponse.getResult().getOutput().getContent();

            // Extract suggested services from AI response
            List<ChatResponse.SuggestedService> suggestedServices = extractSuggestedServices(aiResponse, caseStudies);

            log.info("Received response from Ollama");
            return new ChatResponse(aiResponse, suggestedServices);
        } catch (Exception e) {
            log.error("Error calling Ollama", e);
            throw new RuntimeException("Failed to get AI response: " + e.getMessage(), e);
        }
    }

    private String buildCaseStudyContext(List<CaseStudyWithServicesDto> caseStudies) {
        return caseStudies.stream()
                .flatMap(cs -> cs.services().stream())
                .map(s -> String.format("- %s (ID: %d, Price: %s %s): %s",
                        s.serviceName(), s.serviceId(), s.basePrice(), s.currency(),
                        s.description() != null ? s.description() : "Professional service"))
                .distinct()
                .collect(Collectors.joining("\n"));
    }

    private List<ChatResponse.SuggestedService> extractSuggestedServices(String aiResponse, List<CaseStudyWithServicesDto> caseStudies) {
        // Simple approach: return first 3 services as suggestions
        return caseStudies.stream()
                .flatMap(cs -> cs.services().stream())
                .limit(3)
                .map(service -> new ChatResponse.SuggestedService(
                        service.serviceId(),
                        service.serviceName(),
                        service.discountPercentage(),
                        service.finalPrice()
                ))
                .collect(Collectors.toList());
    }
}
