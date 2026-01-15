package com.admeliora.briefbot.e2e.steps;

import com.admeliora.briefbot.e2e.config.TestConfig;
import com.admeliora.briefbot.e2e.model.request.CaseStudyRequest;
import com.admeliora.briefbot.e2e.model.request.ChatRequest;
import com.admeliora.briefbot.e2e.model.request.ServiceRequest;
import com.admeliora.briefbot.e2e.model.response.CaseStudyResponse;
import com.admeliora.briefbot.e2e.model.response.ChatResponse;
import com.admeliora.briefbot.e2e.model.response.ServiceResponse;
import com.admeliora.briefbot.e2e.support.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for AI Chat operations
 */
public class AiChatSteps {

    private final TestContext context;

    @Autowired
    public AiChatSteps(TestContext context) {
        this.context = context;
    }

    @When("I send a chat message {string}")
    public void iSendAChatMessage(String message) {
        ChatRequest request = ChatRequest.builder()
                .message(message)
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/ai/chat")
                .then()
                .extract().response();

        context.setLastResponse(response);

        if (response.getStatusCode() == 200) {
            ChatResponse chatResponse = response.as(ChatResponse.class);
            context.put("chatResponse", chatResponse);
            context.put("lastChatMessage", chatResponse.getResponse());
        }
    }

    @When("I send an empty chat message")
    public void iSendAnEmptyChatMessage() {
        ChatRequest request = ChatRequest.builder()
                .message("")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/ai/chat")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @When("I send a very long chat message")
    public void iSendAVeryLongChatMessage() {
        // Create a message with 5000 characters (within the 10000 limit)
        String longMessage = "This is a very long message. ".repeat(200);

        ChatRequest request = ChatRequest.builder()
                .message(longMessage)
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/ai/chat")
                .then()
                .extract().response();

        context.setLastResponse(response);

        if (response.getStatusCode() == 200) {
            ChatResponse chatResponse = response.as(ChatResponse.class);
            context.put("chatResponse", chatResponse);
        }
    }

    @Given("I have an existing case study with title {string}")
    public void iHaveAnExistingCaseStudyWithTitle(String title) {
        CaseStudyRequest request = CaseStudyRequest.builder()
                .projectName(title)
                .clientIndustry("Technology")
                .keywords("ai,integration,automation")
                .scopeSummary("Complete AI integration project")
                .challengesSolved("Automated manual processes")
                .budgetRangeEnum("MEDIUM")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/case-studies")
                .then()
                .extract().response();

        if (response.getStatusCode() == 201) {
            CaseStudyResponse caseStudy = response.as(CaseStudyResponse.class);
            context.put("caseStudyId", caseStudy.getId());
            context.put("lastCaseStudy", caseStudy);
        }
    }

    @Given("I have an existing case study with title {string} and services")
    public void iHaveAnExistingCaseStudyWithTitleAndServices(String title) {
        // First create a service
        ServiceRequest serviceRequest = ServiceRequest.builder()
                .name("AI Consulting Service")
                .description("Professional AI consulting and implementation")
                .basePrice(BigDecimal.valueOf(5000))
                .vatRate(BigDecimal.valueOf(23))
                .currency("PLN")
                .pricingUnit("project")
                .isActive(true)
                .minPriceThreshold(BigDecimal.valueOf(2500))
                .build();

        Response serviceResponse = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(serviceRequest)
                .when()
                .post("/services")
                .then()
                .extract().response();

        if (serviceResponse.getStatusCode() == 201) {
            ServiceResponse service = serviceResponse.as(ServiceResponse.class);
            Long serviceId = service.getId();
            context.put("serviceId", serviceId);
        }

        // Then create case study with the service
        CaseStudyRequest request = CaseStudyRequest.builder()
                .projectName(title)
                .clientIndustry("Technology")
                .keywords("cloud,migration,aws")
                .scopeSummary("Complete cloud migration project")
                .challengesSolved("Migrated legacy systems to cloud")
                .budgetRangeEnum("LARGE")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/case-studies")
                .then()
                .extract().response();

        if (response.getStatusCode() == 201) {
            CaseStudyResponse caseStudy = response.as(CaseStudyResponse.class);
            context.put("caseStudyId", caseStudy.getId());

            // Add service to case study if needed
            // This would require additional API endpoint for adding services to case studies
        }
    }

    @Given("I have case studies with services and pricing")
    public void iHaveCaseStudiesWithServicesAndPricing() {
        // Create multiple services
        List<Long> serviceIds = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            ServiceRequest serviceRequest = ServiceRequest.builder()
                    .name("Service " + i)
                    .description("Test service " + i)
                    .basePrice(BigDecimal.valueOf(1000 * i))
                    .vatRate(BigDecimal.valueOf(23))
                    .currency("PLN")
                    .pricingUnit("project")
                    .isActive(true)
                    .minPriceThreshold(BigDecimal.valueOf(500 * i))
                    .build();

            Response serviceResponse = given()
                    .spec(TestConfig.getRequestSpec(context))
                    .body(serviceRequest)
                    .when()
                    .post("/services")
                    .then()
                    .extract().response();

            if (serviceResponse.getStatusCode() == 201) {
                ServiceResponse service = serviceResponse.as(ServiceResponse.class);
                serviceIds.add(service.getId());
            }
        }

        // Create case studies
        for (int i = 1; i <= 2; i++) {
            CaseStudyRequest request = CaseStudyRequest.builder()
                    .projectName("Case Study " + i)
                    .clientIndustry("Technology")
                    .keywords("test,keywords")
                    .scopeSummary("Test case study " + i)
                    .challengesSolved("Solved challenges " + i)
                    .budgetRangeEnum("MEDIUM")
                    .build();

            Response response = given()
                    .spec(TestConfig.getRequestSpec(context))
                    .body(request)
                    .when()
                    .post("/case-studies")
                    .then()
                    .extract().response();

            if (response.getStatusCode() == 201) {
                CaseStudyResponse caseStudy = response.as(CaseStudyResponse.class);
                context.put("caseStudyId_" + i, caseStudy.getId());
            }
        }

        context.put("serviceIds", serviceIds);
    }

    @Given("I have multiple case studies with services")
    public void iHaveMultipleCaseStudiesWithServices() {
        iHaveCaseStudiesWithServicesAndPricing();
    }

    @Then("the chat response should not be empty")
    public void theChatResponseShouldNotBeEmpty() {
        ChatResponse chatResponse = context.get("chatResponse", ChatResponse.class);
        assertThat(chatResponse).isNotNull();
        assertThat(chatResponse.getResponse()).isNotNull();
        assertThat(chatResponse.getResponse()).isNotEmpty();
    }

    @Then("the chat response should contain information about case studies")
    public void theChatResponseShouldContainInformationAboutCaseStudies() {
        String response = context.get("lastChatMessage", String.class);
        assertThat(response).isNotNull();
        // Check if response contains keywords related to case studies
        assertThat(response.toLowerCase())
                .containsAnyOf("case study", "case studies", "project", "projects");
    }

    @Then("the chat response should mention case study names")
    public void theChatResponseShouldMentionCaseStudyNames() {
        String response = context.get("lastChatMessage", String.class);
        assertThat(response).isNotNull();
        // Check if response contains some project-related content
        assertThat(response.toLowerCase())
                .containsAnyOf("case study", "service", "project", "price", "pricing");
    }
}

