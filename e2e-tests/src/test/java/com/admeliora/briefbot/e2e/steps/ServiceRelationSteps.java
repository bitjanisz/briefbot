package com.admeliora.briefbot.e2e.steps;

import com.admeliora.briefbot.e2e.config.TestConfig;
import com.admeliora.briefbot.e2e.model.ServiceRelationRequest;
import com.admeliora.briefbot.e2e.model.ServiceRelationResponse;
import com.admeliora.briefbot.e2e.model.ServiceRequest;
import com.admeliora.briefbot.e2e.support.TestContext;
import io.cucumber.java.en.And;
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
 * Step definitions for Service Relation operations
 */
public class ServiceRelationSteps {

    private final TestContext context;

    @Autowired
    public ServiceRelationSteps(TestContext context) {
        this.context = context;
    }

    @Given("I have two existing services")
    public void iHaveTwoExistingServices() {
        // Create first service
        ServiceRequest serviceARequest = ServiceRequest.builder()
                .name("Service A")
                .description("Test service A")
                .accountId(TestConfig.getDefaultAccountId())
                .basePrice(BigDecimal.valueOf(1000.00))
                .vatRate(BigDecimal.valueOf(23.00))
                .currency("PLN")
                .pricingUnit("project")
                .isActive(true)
                .relations(new ArrayList<>())
                .build();

        Response response1 = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(serviceARequest)
                .when()
                .post("/services");

        assertThat(response1.getStatusCode()).isEqualTo(201);
        Long serviceAId = response1.jsonPath().getLong("id");
        context.put("serviceAId", serviceAId);

        // Create second service
        ServiceRequest serviceBRequest = ServiceRequest.builder()
                .name("Service B")
                .description("Test service B")
                .accountId(TestConfig.getDefaultAccountId())
                .basePrice(BigDecimal.valueOf(2000.00))
                .vatRate(BigDecimal.valueOf(23.00))
                .currency("PLN")
                .pricingUnit("project")
                .isActive(true)
                .relations(new ArrayList<>())
                .build();

        Response response2 = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(serviceBRequest)
                .when()
                .post("/services");

        assertThat(response2.getStatusCode()).isEqualTo(201);
        Long serviceBId = response2.jsonPath().getLong("id");
        context.put("serviceBId", serviceBId);
    }

    @When("I create a relation between them with type {string} and impact {string}")
    public void iCreateARelationBetweenThemWithTypeAndImpact(String relationType, String impact) {
        Long serviceAId = (Long) context.get("serviceAId");
        Long serviceBId = (Long) context.get("serviceBId");

        ServiceRelationRequest request = ServiceRelationRequest.builder()
                .serviceId(serviceAId)
                .relatedServiceId(serviceBId)
                .relationType(relationType)
                .impactDescription(impact)
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/service-relations")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the relation should be created")
    public void theRelationShouldBeCreated() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(201);

        ServiceRelationResponse relation = response.as(ServiceRelationResponse.class);
        assertThat(relation.getId()).isNotNull();
        assertThat(relation.getServiceId()).isNotNull();
        assertThat(relation.getRelatedServiceId()).isNotNull();
        assertThat(relation.getRelationType()).isNotNull();

        context.put("lastRelationId", relation.getId());
    }

    @Given("I have a service with relations")
    public void iHaveAServiceWithRelations() {
        // This step would create a service and add relations to it
        // For now, assume we have one from previous steps
    }

    @When("I get relations for that service")
    public void iGetRelationsForThatService() {
        Long serviceId = (Long) context.get("serviceAId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/services/{serviceId}/relations", serviceId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @And("the response should contain service relations")
    public void theResponseShouldContainServiceRelations() {
        Response response = context.getLastResponse();
        List<ServiceRelationResponse> relations = response.jsonPath().getList("", ServiceRelationResponse.class);
        assertThat(relations).isNotEmpty();
    }

    @Given("I have an existing service relation")
    public void iHaveAnExistingServiceRelation() {
        // Assume we have one from previous steps or create one
        if (context.get("lastRelationId") == null) {
            iHaveTwoExistingServices();
            iCreateARelationBetweenThemWithTypeAndImpact("DEPENDENCY", "HIGH");
        }
    }

    @When("I delete the relation")
    public void iDeleteTheRelation() {
        Long relationId = (Long) context.get("lastRelationId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .delete("/service-relations/{relationId}", relationId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @When("I create a relation with invalid service IDs")
    public void iCreateARelationWithInvalidServiceIDs() {
        ServiceRelationRequest request = ServiceRelationRequest.builder()
                .serviceId(99999L)
                .relatedServiceId(99998L)
                .relationType("DEPENDENCY")
                .impactDescription("HIGH")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/service-relations")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Given("I have services A, B, C")
    public void iHaveServicesABC() {
        // Create service A
        ServiceRequest serviceARequest = ServiceRequest.builder()
                .name("Service A")
                .description("Test service A")
                .accountId(TestConfig.getDefaultAccountId())
                .basePrice(BigDecimal.valueOf(1000.00))
                .vatRate(BigDecimal.valueOf(23.00))
                .currency("PLN")
                .pricingUnit("project")
                .isActive(true)
                .relations(new ArrayList<>())
                .build();

        Response responseA = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(serviceARequest)
                .when()
                .post("/services");
        Long serviceAId = responseA.jsonPath().getLong("id");
        context.put("serviceAId", serviceAId);

        // Create service B
        ServiceRequest serviceBRequest = ServiceRequest.builder()
                .name("Service B")
                .description("Test service B")
                .accountId(TestConfig.getDefaultAccountId())
                .basePrice(BigDecimal.valueOf(2000.00))
                .vatRate(BigDecimal.valueOf(23.00))
                .currency("PLN")
                .pricingUnit("project")
                .isActive(true)
                .relations(new ArrayList<>())
                .build();

        Response responseB = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(serviceBRequest)
                .when()
                .post("/services");
        Long serviceBId = responseB.jsonPath().getLong("id");
        context.put("serviceBId", serviceBId);

        // Create service C
        ServiceRequest serviceCRequest = ServiceRequest.builder()
                .name("Service C")
                .description("Test service C")
                .accountId(TestConfig.getDefaultAccountId())
                .basePrice(BigDecimal.valueOf(3000.00))
                .vatRate(BigDecimal.valueOf(23.00))
                .currency("PLN")
                .pricingUnit("project")
                .isActive(true)
                .relations(new ArrayList<>())
                .build();

        Response responseC = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(serviceCRequest)
                .when()
                .post("/services");
        Long serviceCId = responseC.jsonPath().getLong("id");
        context.put("serviceCId", serviceCId);
    }

    @When("I try to make C depend on A")
    public void iTryToMakeCDependOnA() {
        Long serviceCId = (Long) context.get("serviceCId");
        Long serviceAId = (Long) context.get("serviceAId");

        ServiceRelationRequest request = ServiceRelationRequest.builder()
                .serviceId(serviceCId)
                .relatedServiceId(serviceAId)
                .relationType("DEPENDENCY")
                .impactDescription("HIGH")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/service-relations")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }
}
