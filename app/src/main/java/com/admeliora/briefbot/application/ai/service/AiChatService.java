package com.admeliora.briefbot.application.ai.service;

import com.admeliora.briefbot.adapter.in.web.ai.model.ChatResponse;
import com.admeliora.briefbot.application.ai.model.CaseStudyWithServicesDto;
import com.admeliora.briefbot.application.ai.port.out.CaseStudyWithServicesPort;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiChatService {

    private final OllamaChatModel chatModel;
    private final CaseStudyWithServicesPort caseStudyWithServicesPort;
    private final ObjectMapper objectMapper;

    // Constants for response format
    private static final String RECOMMENDATIONS_SEPARATOR = "<!-- recommendations -->";
    private static final String JSON_FORMAT_EXAMPLE = """
            [
              {"id": service_id, "originalName": "original_service_name", "translatedName": "service_name_in_user_language", "calculatedDiscount": discount_percentage},
              ...
            ]""".trim();

    public ChatResponse chat(String userMessage) {
        try {
            log.info("Sending message to Ollama: {}", userMessage);

            // Get all case studies with services for context
            List<CaseStudyWithServicesDto> caseStudies = caseStudyWithServicesPort.getAllCaseStudiesWithServices();

            // Build context from case studies and services
            String caseStudiesContext = buildCaseStudiesContext(caseStudies);
            String servicesContext = buildServicesContext(caseStudies);

            // Create system prompt template
            String template = """
                    You are a helpful assistant for a business services company.
                    Analyze the customer's request and suggest relevant services based on our case studies.
                    IMPORTANT: Detect the language of the user's message and respond in the SAME language.
                    
                    CASE STUDIES:
                    {caseStudies}
                    
                    AVAILABLE SERVICES:
                    {services}
                    
                    RESPONSE FORMAT:
                    First, provide your response text to the user.
                    Then, add a separator with the following line: {separator}
                    Finally, provide a JSON array of suggested services in this exact format:
                    {jsonFormat}
                    
                    Rules:
                    - Only suggest services that exist in the AVAILABLE SERVICES list
                    - For originalName: use the exact name from AVAILABLE SERVICES
                    - For translatedName: provide service names in the user's language (detect from their message)
                    - For calculatedDiscount: use the discount percentage from the relevant case studies (look at the CASE STUDIES data)
                    - Suggest multiple services when appropriate (at least 2-3)
                    - Base suggestions on case study relevance
                    """;

            PromptTemplate promptTemplate = new PromptTemplate(template);
            promptTemplate.add("caseStudies", caseStudiesContext);
            promptTemplate.add("services", servicesContext);
            promptTemplate.add("separator", RECOMMENDATIONS_SEPARATOR);
            promptTemplate.add("jsonFormat", JSON_FORMAT_EXAMPLE);

            // Create prompt with messages
            List<org.springframework.ai.chat.messages.Message> messages = List.of(
                new SystemMessage(promptTemplate.render()),
                new UserMessage(userMessage)
            );
            Prompt prompt = new Prompt(messages, OllamaOptions.builder().build());

            var chatResponse = chatModel.call(prompt);
            String aiResponse = chatResponse.getResult().getOutput().getContent();

            // Split response to get user text (before separator)
            String userText = aiResponse.split(RECOMMENDATIONS_SEPARATOR)[0].trim();

            // Extract suggested services from AI response
            List<ChatResponse.SuggestedService> suggestedServices = extractSuggestedServices(aiResponse, caseStudies);

            log.info("Received response from Ollama");
            return new ChatResponse(userText, suggestedServices);
        } catch (Exception e) {
            log.error("Error calling Ollama", e);
            // Add error message to user text when exception occurs
            String errorMessage = "\n\nPrzepraszam, nie udało mi się wygenerować konkretnych sugestii usług. Proszę spróbować ponownie lub skontaktować się z naszym zespołem.";
            return new ChatResponse(errorMessage, List.of());
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

    private String buildCaseStudiesContext(List<CaseStudyWithServicesDto> caseStudies) {
        try {
            List<CaseStudyJson> caseStudyJsons = caseStudies.stream()
                .filter(cs -> !cs.services().isEmpty())
                .map(cs -> new CaseStudyJson(
                    cs.id(),
                    cs.projectName(),
                    cs.scopeSummary(),
                    cs.services().stream()
                        .map(s -> new CaseStudyServiceJson(s.serviceId(), s.serviceName(), s.discountPercentage().doubleValue(), s.currency()))
                        .collect(Collectors.toList())
                ))
                .distinct()
                .collect(Collectors.toList());

            return objectMapper.writeValueAsString(caseStudyJsons);
        } catch (Exception e) {
            log.warn("Failed to serialize case studies to JSON, using fallback: {}", e.getMessage());
            return "[]";
        }
    }

    private String buildServicesContext(List<CaseStudyWithServicesDto> caseStudies) {
        try {
            List<ServiceJson> serviceJsons = caseStudies.stream()
                .flatMap(cs -> cs.services().stream())
                .map(s -> new ServiceJson(s.serviceId(), s.serviceName(), s.description(), s.basePrice(), s.currency()))
                .distinct()
                .collect(Collectors.toList());

            return objectMapper.writeValueAsString(serviceJsons);
        } catch (Exception e) {
            log.warn("Failed to serialize services to JSON, using fallback: {}", e.getMessage());
            return "[]";
        }
    }

    private List<ChatResponse.SuggestedService> extractSuggestedServices(String aiResponse, List<CaseStudyWithServicesDto> caseStudies) {
        try {
            // Split response by separator
            String[] parts = aiResponse.split(RECOMMENDATIONS_SEPARATOR);
            if (parts.length < 2) {
                // Fallback if no separator found
                log.warn("No separator found in AI response, returning empty list");
                return List.of();
            }

            // Extract JSON part (after separator)
            String jsonPart = parts[1].trim();

            // Parse JSON array
            List<AiSuggestedService> aiSuggestions = objectMapper.readValue(
                jsonPart,
                new TypeReference<List<AiSuggestedService>>() {}
            );

            // Get all available services for validation
            List<CaseStudyWithServicesDto.ServiceDto> allServices = caseStudies.stream()
                .flatMap(cs -> cs.services().stream())
                .distinct()
                .collect(Collectors.toList());

            // Map to actual services and create response - only include valid existing services
            return aiSuggestions.stream()
                .map(aiSuggestion -> {
                    // Find the actual service by ID from all available services
                    var actualService = allServices.stream()
                        .filter(service -> service.serviceId().equals(aiSuggestion.id))
                        .findFirst();

                    if (actualService.isPresent()) {
                        var service = actualService.get();
                        String originalName = service.serviceName();
                        String translatedName = aiSuggestion.translatedName != null ? aiSuggestion.translatedName : "";

                        // If language is different than English, use translated name, otherwise set empty value
                        // We determine this by checking if translatedName is different from originalName
                        String finalTranslatedName = !translatedName.equals(originalName) && !translatedName.isEmpty() ? translatedName : "";

                        return new ChatResponse.SuggestedService(
                            service.serviceId(),
                            originalName, // Use actual service name as originalName
                            finalTranslatedName, // Use translated name only if different from original (indicating non-English language)
                            BigDecimal.valueOf(aiSuggestion.calculatedDiscount == null ? 0.0 : aiSuggestion.calculatedDiscount),
                            service.finalPrice()
                        );
                    } else {
                        // Skip services that don't exist in our database
                        log.warn("AI suggested service ID {} which doesn't exist in available services, skipping", aiSuggestion.id);
                        return null;
                    }
                })
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());

        } catch (Exception e) {
            log.warn("Failed to parse AI suggested services, returning empty list: {}", e.getMessage());
            return List.of(); // Return empty list instead of default suggestions
        }
    }

    // JSON record classes
    public record CaseStudyJson(Long id, String projectName, String scopeSummary, List<CaseStudyServiceJson> services) {}
    public record CaseStudyServiceJson(Long id, String name, Double discount, String currency) {}
    public record ServiceJson(Long id, String name, String description, BigDecimal price, String currency) {}

    // Record for AI's suggested service format
    public record AiSuggestedService(Long id, String originalName, String translatedName, Double calculatedDiscount) {}
}
