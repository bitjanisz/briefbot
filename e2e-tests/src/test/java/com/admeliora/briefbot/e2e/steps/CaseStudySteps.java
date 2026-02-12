package com.admeliora.briefbot.e2e.steps;

import com.admeliora.briefbot.e2e.config.TestConfig;
import com.admeliora.briefbot.e2e.model.request.CaseStudyRequest;
import com.admeliora.briefbot.e2e.model.request.PublishRequest;
import com.admeliora.briefbot.e2e.model.response.CaseStudyResponse;
import com.admeliora.briefbot.e2e.model.request.CaseStudyUpdateRequest;
import com.admeliora.briefbot.e2e.model.request.ServiceRequest;
import com.admeliora.briefbot.e2e.model.response.ServiceResponse;
import com.admeliora.briefbot.e2e.model.CaseStudyStatus;
import com.admeliora.briefbot.e2e.support.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Case Study operations
 */
public class CaseStudySteps {

    private final TestContext context;

    @Autowired
    public CaseStudySteps(TestContext context) {
        this.context = context;
    }

    @When("I create a case study with title {string}")
    public void iCreateACaseStudyWithTitle(String title) {
        CaseStudyRequest request = CaseStudyRequest.builder()
                .projectName(title)
                .clientIndustry("Test Industry")
                .keywords("test,keywords")
                .scopeSummary("Test scope summary")
                .challengesSolved("Test challenges solved")
                .budgetRangeEnum("SMALL")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/case-studies")
                .then()
                .extract().response();

        context.put("caseStudyId", response.jsonPath().getLong("id"));
        context.setLastResponse(response);
    }

    @When("I create a case study with title {string} and services")
    public void iCreateACaseStudyWithTitleAndServices(String title) {
        // First, create a service if not exists
        if (context.get("serviceId") == null) {
            // Create a service
            ServiceRequest serviceRequest = ServiceRequest.builder()
                    .name("Test Service")
                    .description("Test service description")
                    .accountId(TestConfig.getDefaultAccountId())
                    .basePrice(BigDecimal.valueOf(1000))
                    .vatRate(BigDecimal.valueOf(23))
                    .currency("PLN")
                    .pricingUnit("project")
                    .isActive(true)
                    .minPriceThreshold(BigDecimal.valueOf(500))
//                    .relations(new ArrayList<>())
                    .build();

            Response serviceResponse = given()
                    .spec(TestConfig.getRequestSpec(context))
                    .body(serviceRequest)
                    .when()
                    .post("/services")
                    .then()
                    .extract().response();

            if (serviceResponse.getStatusCode() == 201) {
                ServiceResponse createdService = serviceResponse.as(ServiceResponse.class);
                context.setCreatedId("service", createdService.getId());
                context.put("lastService", createdService);
            }
        }

        Long serviceId = context.getCreatedId("service");
        assertThat(serviceId).as("Service ID should exist").isNotNull();

        // Now create case study with the service
        CaseStudyRequest.Service service = CaseStudyRequest.Service.builder()
                .serviceId(serviceId)
                .discountPercentage(BigDecimal.valueOf(10.00))
                .build();

        CaseStudyRequest request = CaseStudyRequest.builder()
                .projectName(title)
                .clientIndustry("Test Industry with Services")
                .keywords("test,keywords,services")
                .scopeSummary("Test scope summary with services")
                .challengesSolved("Test challenges solved with services")
                .budgetRangeEnum("MEDIUM")
                .services(List.of(service))
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/case-studies")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the case study should be created")
    public void theCaseStudyShouldBeCreated() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(201);

        CaseStudyResponse caseStudy = response.as(CaseStudyResponse.class);
        assertThat(caseStudy.getId()).isNotNull();

        context.put("caseStudyId", caseStudy.getId());
    }

    @Given("I have an existing case study")
    public void iHaveAnExistingCaseStudy() {
        if (context.get("caseStudyId") == null) {
            iCreateACaseStudyWithTitle("Test Case Study");
            assertThat(context.getLastResponse().getStatusCode()).isEqualTo(201);
        }
    }

    @When("I get the case study by id")
    public void iGetTheCaseStudyById() {
        Long caseStudyId = (Long) context.get("caseStudyId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/case-studies/{caseStudyId}", caseStudyId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the response should contain the case study details")
    public void theResponseShouldContainTheCaseStudyDetails() {
        Response response = context.getLastResponse();
        CaseStudyResponse caseStudy = response.as(CaseStudyResponse.class);
        assertThat(caseStudy.getId()).isNotNull();
        assertThat(caseStudy.getProjectName()).isNotNull();
    }

    @When("I update the case study description and results")
    public void iUpdateTheCaseStudyDescriptionAndResults() {
        Long caseStudyId = (Long) context.get("caseStudyId");

        CaseStudyUpdateRequest request = CaseStudyUpdateRequest.builder()
                .id(caseStudyId)
                .projectName("Updated Project Name")
                .clientIndustry("Updated Industry")
                .keywords("updated,keywords")
                .scopeSummary("Updated scope summary")
                .challengesSolved("Updated challenges solved")
                .budgetRangeEnum("MEDIUM")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/case-studies/{caseStudyId}", caseStudyId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @When("I update the case study to add services with discount {double}")
    public void iUpdateTheCaseStudyToAddServicesWithDiscount(Double discount) {
        Long caseStudyId = (Long) context.get("caseStudyId");

        @SuppressWarnings("unchecked")
        List<Long> expectedServiceIds = (List<Long>) context.get("serviceIds");
        if (expectedServiceIds == null || expectedServiceIds.isEmpty()) {
            // Fallback to single service
            Long serviceId = context.getCreatedId("service");
            expectedServiceIds = serviceId != null ? List.of(serviceId) : List.of();
        }

        List<CaseStudyUpdateRequest.Service> services = expectedServiceIds.stream()
                .map(serviceId -> CaseStudyUpdateRequest.Service.builder()
                        .serviceId(serviceId)
                        .discountPercentage(BigDecimal.valueOf(discount))
                        .build())
                .toList();

        CaseStudyUpdateRequest request = CaseStudyUpdateRequest.builder()
                .id(caseStudyId)
                .projectName("Updated Project Name with Services")
                .clientIndustry("Updated Industry with Services")
                .keywords("updated,keywords,services")
                .scopeSummary("Updated scope summary with services")
                .challengesSolved("Updated challenges solved with services")
                .budgetRangeEnum("LARGE")
                .services(services)
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/case-studies/{caseStudyId}", caseStudyId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the case study should be updated")
    public void theCaseStudyShouldBeUpdated() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @When("I list all case studies for the default account")
    public void iListAllCaseStudiesForTheDefaultAccount() {
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .queryParam("accountId", TestConfig.getDefaultAccountId())
                .when()
                .get("/case-studies")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the response should contain a list of case studies")
    public void theResponseShouldContainAListOfCaseStudies() {
        Response response = context.getLastResponse();
        List<CaseStudyResponse> caseStudies = response.jsonPath().getList("", CaseStudyResponse.class);
        assertThat(caseStudies).isNotNull();
    }

    @Given("I have a draft case study")
    public void iHaveADraftCaseStudy() {
        iHaveAnExistingCaseStudy();
        // Assume it's draft by default
    }

    @When("I publish the case study")
    public void iPublishTheCaseStudy() {
        Long caseStudyId = (Long) context.get("caseStudyId");

        PublishRequest request = PublishRequest.builder()
                .status(CaseStudyStatus.PUBLISHED)
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/case-studies/{caseStudyId}/publish", caseStudyId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the case study should be marked as published")
    public void theCaseStudyShouldBeMarkedAsPublished() {
        Response response = context.getLastResponse();
        CaseStudyResponse caseStudy = response.as(CaseStudyResponse.class);
        assertThat(caseStudy.getStatus()).isEqualTo(CaseStudyStatus.PUBLISHED);
    }

    @When("I create a case study with empty title")
    public void iCreateACaseStudyWithEmptyTitle() {
        CaseStudyRequest request = CaseStudyRequest.builder()
                .projectName("")
                .clientIndustry("Test Industry")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/case-studies")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Given("I have a case study without results")
    public void iHaveACaseStudyWithoutResults() {
        CaseStudyRequest request = CaseStudyRequest.builder()
                .projectName("Incomplete Case Study")
                .clientIndustry("Test Industry")
                .keywords("incomplete")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/case-studies");

        Long caseStudyId = response.jsonPath().getLong("id");
        context.put("caseStudyId", caseStudyId);
    }

    @When("I try to publish it")
    public void iTryToPublishIt() {
        Long caseStudyId = (Long) context.get("caseStudyId");

        PublishRequest request = PublishRequest.builder()
                .status(CaseStudyStatus.PUBLISHED)
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/case-studies/{caseStudyId}/publish", caseStudyId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the case study should contain services")
    public void theCaseStudyShouldContainServices() {
        Response response = context.getLastResponse();
        CaseStudyResponse caseStudy = response.as(CaseStudyResponse.class);
        assertThat(caseStudy.getServices()).isNotNull();
        assertThat(caseStudy.getServices()).isNotEmpty();
        Long expectedServiceId = context.getCreatedId("service");
        assertThat(caseStudy.getServices().get(0).getServiceId()).isEqualTo(expectedServiceId);
        assertThat(caseStudy.getServices().get(0).getDiscountPercentage()).isEqualTo(BigDecimal.valueOf(10.00));
    }

    @Then("the case study should contain services with the following details:")
    public void theCaseStudyShouldContainServicesWithDetails(DataTable dataTable) {
        Response response = context.getLastResponse();
        CaseStudyResponse caseStudy = response.as(CaseStudyResponse.class);
        assertThat(caseStudy.getServices()).isNotNull();
        assertThat(caseStudy.getServices()).isNotEmpty();

        Long expectedServiceId = context.getCreatedId("service");
        // Check that the expected service is present
        boolean serviceFound = caseStudy.getServices().stream()
                .anyMatch(service -> service.getServiceId().equals(expectedServiceId));
        assertThat(serviceFound).as("Expected service should be present").isTrue();

        // Process the data table
        List<Map<String, String>> table = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : table) {
            String field = row.get("field");
            String expectedValue = row.get("value");

            // Check that all services have this field with the expected value
            for (CaseStudyResponse.Service service : caseStudy.getServices()) {
                try {
                    // Use reflection to get the field value
                    java.lang.reflect.Field fieldObj = service.getClass().getDeclaredField(field);
                    fieldObj.setAccessible(true);
                    Object actualValue = fieldObj.get(service);

                    // Convert expected value to appropriate type
                    if (actualValue instanceof BigDecimal) {
                        assertThat((BigDecimal) actualValue).isEqualTo(new BigDecimal(expectedValue));
                    } else if (actualValue instanceof String) {
                        assertThat((String) actualValue).isEqualTo(expectedValue);
                    } else if (actualValue instanceof Long) {
                        assertThat((Long) actualValue).isEqualTo(Long.valueOf(expectedValue));
                    } else if (actualValue instanceof Integer) {
                        assertThat((Integer) actualValue).isEqualTo(Integer.valueOf(expectedValue));
                    } else if (actualValue instanceof Boolean) {
                        assertThat((Boolean) actualValue).isEqualTo(Boolean.valueOf(expectedValue));
                    } else {
                        // For other types, convert to string and compare
                        assertThat(actualValue.toString()).isEqualTo(expectedValue);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Failed to check field " + field + " for service " + service.getServiceId(), e);
                }
            }
        }
    }
}
