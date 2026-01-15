package com.admeliora.briefbot.application.ai.function;

import com.admeliora.briefbot.application.ai.model.CaseStudyWithServicesDto;
import com.admeliora.briefbot.application.ai.port.out.CaseStudyWithServicesPort;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.List;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class CaseStudyFunctions {

    private final CaseStudyWithServicesPort caseStudyWithServicesPort;

    @Bean
    @Description("Get all case studies with their associated services including pricing and discount information")
    public Function<CaseStudyRequest, CaseStudyResponse> getCaseStudiesWithServices() {
        return request -> {
            if (request.caseStudyId() != null) {
                return caseStudyWithServicesPort.getCaseStudyWithServices(request.caseStudyId())
                        .map(cs -> new CaseStudyResponse(List.of(cs)))
                        .orElse(new CaseStudyResponse(List.of()));
            } else {
                List<CaseStudyWithServicesDto> caseStudies = caseStudyWithServicesPort.getAllCaseStudiesWithServices();
                return new CaseStudyResponse(caseStudies);
            }
        };
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonClassDescription("Request for retrieving case studies with services")
    public record CaseStudyRequest(
            @JsonPropertyDescription("Optional case study ID. If provided, returns specific case study. If null, returns all case studies")
            Long caseStudyId
    ) {
    }

    @JsonClassDescription("Response containing case studies with their services")
    public record CaseStudyResponse(
            @JsonPropertyDescription("List of case studies with their associated services")
            List<CaseStudyWithServicesDto> caseStudies
    ) {
    }
}

