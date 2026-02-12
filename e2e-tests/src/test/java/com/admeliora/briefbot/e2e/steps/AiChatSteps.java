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
        // Create multiple services with different types
        List<Long> serviceIds = new ArrayList<>();

        // Web Development Service
        ServiceRequest webDevService = ServiceRequest.builder()
                .name("Web Development Service")
                .description("Complete web application development including frontend and backend")
                .basePrice(BigDecimal.valueOf(15000))
                .vatRate(BigDecimal.valueOf(23))
                .currency("PLN")
                .pricingUnit("project")
                .isActive(true)
                .minPriceThreshold(BigDecimal.valueOf(7500))
                .build();

        Response webDevResponse = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(webDevService)
                .when()
                .post("/services")
                .then()
                .extract().response();

        if (webDevResponse.getStatusCode() == 201) {
            ServiceResponse service = webDevResponse.as(ServiceResponse.class);
            serviceIds.add(service.getId());
        }

        // Mobile App Development Service
        ServiceRequest mobileService = ServiceRequest.builder()
                .name("Mobile App Development")
                .description("Native mobile application development for iOS and Android")
                .basePrice(BigDecimal.valueOf(25000))
                .vatRate(BigDecimal.valueOf(23))
                .currency("PLN")
                .pricingUnit("project")
                .isActive(true)
                .minPriceThreshold(BigDecimal.valueOf(12500))
                .build();

        Response mobileResponse = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(mobileService)
                .when()
                .post("/services")
                .then()
                .extract().response();

        if (mobileResponse.getStatusCode() == 201) {
            ServiceResponse service = mobileResponse.as(ServiceResponse.class);
            serviceIds.add(service.getId());
        }

        // Cloud Migration Service
        ServiceRequest cloudService = ServiceRequest.builder()
                .name("Cloud Migration Service")
                .description("Complete migration of existing systems to cloud infrastructure")
                .basePrice(BigDecimal.valueOf(30000))
                .vatRate(BigDecimal.valueOf(23))
                .currency("PLN")
                .pricingUnit("project")
                .isActive(true)
                .minPriceThreshold(BigDecimal.valueOf(15000))
                .build();

        Response cloudResponse = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(cloudService)
                .when()
                .post("/services")
                .then()
                .extract().response();

        if (cloudResponse.getStatusCode() == 201) {
            ServiceResponse service = cloudResponse.as(ServiceResponse.class);
            serviceIds.add(service.getId());
        }

        // AI Integration Service
        ServiceRequest aiService = ServiceRequest.builder()
                .name("AI Integration Service")
                .description("Integration of AI/ML capabilities into existing business processes")
                .basePrice(BigDecimal.valueOf(35000))
                .vatRate(BigDecimal.valueOf(23))
                .currency("PLN")
                .pricingUnit("project")
                .isActive(true)
                .minPriceThreshold(BigDecimal.valueOf(17500))
                .build();

        Response aiResponse = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(aiService)
                .when()
                .post("/services")
                .then()
                .extract().response();

        if (aiResponse.getStatusCode() == 201) {
            ServiceResponse service = aiResponse.as(ServiceResponse.class);
            serviceIds.add(service.getId());
        }

        // Consulting Service
        ServiceRequest consultingService = ServiceRequest.builder()
                .name("IT Consulting Service")
                .description("Expert IT consulting and strategic planning services")
                .basePrice(BigDecimal.valueOf(500))
                .vatRate(BigDecimal.valueOf(23))
                .currency("PLN")
                .pricingUnit("hour")
                .isActive(true)
                .minPriceThreshold(BigDecimal.valueOf(250))
                .build();

        Response consultingResponse = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(consultingService)
                .when()
                .post("/services")
                .then()
                .extract().response();

        if (consultingResponse.getStatusCode() == 201) {
            ServiceResponse service = consultingResponse.as(ServiceResponse.class);
            serviceIds.add(service.getId());
        }

        // Create diverse case studies
        createCaseStudy("E-commerce Platform Development",
                "Technology",
                "ecommerce,web development,react,node.js",
                "Complete e-commerce platform with payment integration, inventory management, and customer portal",
                "Successfully launched a scalable e-commerce solution handling 10k+ daily transactions",
                "LARGE");

        createCaseStudy("Healthcare Mobile App",
                "Healthcare",
                "mobile,ios,android,healthcare",
                "Patient management mobile application with appointment scheduling and telemedicine features",
                "Improved patient engagement by 300% and reduced no-show rates by 40%",
                "LARGE");

        createCaseStudy("Financial Services Cloud Migration",
                "Finance",
                "cloud,aws,migration,security",
                "Migration of legacy banking systems to secure cloud infrastructure with zero downtime",
                "Achieved 99.9% uptime and 60% cost reduction in infrastructure expenses",
                "LARGE");

        createCaseStudy("AI-Powered Analytics Platform",
                "Technology",
                "ai,machine learning,analytics,big data",
                "Development of predictive analytics platform using machine learning for business intelligence",
                "Increased operational efficiency by 45% through data-driven decision making",
                "LARGE");

        createCaseStudy("SaaS Product Launch",
                "Technology",
                "saas,web application,subscription model",
                "End-to-end development of B2B SaaS product with multi-tenant architecture",
                "Reached 500+ paying customers within first year of launch",
                "MEDIUM");

        context.put("serviceIds", serviceIds);
    }

    private void createCaseStudy(String projectName, String clientIndustry, String keywords,
                                String scopeSummary, String challengesSolved, String budgetRange) {
        CaseStudyRequest request = CaseStudyRequest.builder()
                .projectName(projectName)
                .clientIndustry(clientIndustry)
                .keywords(keywords)
                .scopeSummary(scopeSummary)
                .challengesSolved(challengesSolved)
                .budgetRangeEnum(budgetRange)
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
            context.put("caseStudyId_" + projectName.replaceAll("\\s+", "_"), caseStudy.getId());
        }
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

    @Then("the response should include suggested services with IDs and discounts")
    public void theResponseShouldIncludeSuggestedServicesWithIdsAndDiscounts() {
        ChatResponse chatResponse = context.get("chatResponse", ChatResponse.class);
        assertThat(chatResponse).isNotNull();
        assertThat(chatResponse.getSuggestedServices()).isNotNull();
        assertThat(chatResponse.getSuggestedServices()).isNotEmpty();

        // Check that services have required fields
        chatResponse.getSuggestedServices().forEach(service -> {
            assertThat(service.getServiceId()).isNotNull();
            assertThat(service.getServiceName()).isNotNull();
            assertThat(service.getDiscountPercentage()).isNotNull();
            assertThat(service.getFinalPrice()).isNotNull();
        });
    }
}
