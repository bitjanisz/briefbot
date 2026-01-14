package com.admeliora.briefbot.e2e.steps;

import com.admeliora.briefbot.e2e.config.TestConfig;
import com.admeliora.briefbot.e2e.model.request.ServiceRequest;
import com.admeliora.briefbot.e2e.model.response.ServiceResponse;
import com.admeliora.briefbot.e2e.support.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Service CRUD operations
 */
public class ServiceSteps {

    private final TestContext context;

    @Autowired
    public ServiceSteps(TestContext context) {
        this.context = context;
    }

    @Given("I create a service with the following details:")
    public void iCreateAServiceWithDetails(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> table = dataTable.asMaps(String.class, String.class);
        if (table.isEmpty()) {
            throw new IllegalArgumentException("Service details table cannot be empty");
        }

        // Create only one service using the first row
        Map<String, String> row = table.get(0);
        String name = row.get("name");
        Double price = Double.valueOf(row.get("price"));

        ServiceRequest request = ServiceRequest.builder()
                .name(name)
                .description("Test service description")
                .accountId(TestConfig.getDefaultAccountId())
                .basePrice(BigDecimal.valueOf(price))
                .vatRate(BigDecimal.valueOf(23))
                .currency("PLN")
                .pricingUnit("project")
                .isActive(true)
                .minPriceThreshold(BigDecimal.valueOf(1000))
//                    .relations(new ArrayList<>())
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/services")
                .then()
                .extract()
                .response();

        if (response.getStatusCode() == 201) {
            ServiceResponse serviceResponse = response.as(ServiceResponse.class);
            context.setCreatedId("service", serviceResponse.getId());
            context.put("lastService", serviceResponse);
        } else {
            throw new IllegalStateException("Service creation failed for " + name + " with status: " + response.getStatusCode());
        }
    }

    @When("I create a service with name {string} and price {double}")
    public void iCreateAServiceWithNameAndPrice(String name, Double price) {
        ServiceRequest request = ServiceRequest.builder()
                .name(name)
                .description("Test service description")
                .accountId(TestConfig.getDefaultAccountId())
                .basePrice(BigDecimal.valueOf(price))
                .vatRate(BigDecimal.valueOf(23))
                .currency("PLN")
                .pricingUnit("project")
                .isActive(true)
                .minPriceThreshold(BigDecimal.valueOf(1000))
//                .relations(new ArrayList<>())
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/services")
                .then()
                .extract()
                .response();

        context.setLastResponse(response);

        if (response.getStatusCode() == 201) {
            ServiceResponse serviceResponse = response.as(ServiceResponse.class);
            context.setCreatedId("service", serviceResponse.getId());
            context.put("lastService", serviceResponse);
        } else {
            throw new IllegalStateException("Service creation failed with status: " + response.getStatusCode());
        }
    }

    @When("I create a service with name {string} that depends on the previous service")
    public void iCreateAServiceThatDependsOnPrevious(String name) {
        ServiceResponse previousService = context.get("lastService", ServiceResponse.class);
        assertThat(previousService).as("Previous service should exist").isNotNull();

//        List<ServiceRequest.ServiceRelation> relations = new ArrayList<>();
//        relations.add(ServiceRequest.ServiceRelation.builder()
//                .relatedServiceId(previousService.getId())
//                .relationType("DEPENDS_ON")
//                .impactDescription("Requires backend development")
//                .build());

        ServiceRequest request = ServiceRequest.builder()
                .name(name)
                .description("Service with dependencies")
                .accountId(TestConfig.getDefaultAccountId())
                .basePrice(BigDecimal.valueOf(5000))
                .vatRate(BigDecimal.valueOf(23))
                .currency("PLN")
                .pricingUnit("project")
                .isActive(true)
//                .relations(relations)
                .minPriceThreshold(BigDecimal.valueOf(1000))
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/services")
                .then()
                .extract()
                .response();

        context.setLastResponse(response);
    }

    @When("I get the service by id")
    public void iGetTheServiceById() {
        Long serviceId = context.getCreatedId("service");
        assertThat(serviceId).as("Service ID should exist").isNotNull();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/services/" + serviceId)
                .then()
                .extract()
                .response();

        context.setLastResponse(response);
    }

    @When("I update the service price to {double}")
    public void iUpdateTheServicePriceTo(Double newPrice) {
        Long serviceId = context.getCreatedId("service");
        ServiceResponse currentService = context.get("lastService", ServiceResponse.class);

        ServiceRequest request = ServiceRequest.builder()
                .name(currentService.getName())
                .description(currentService.getDescription())
                .basePrice(BigDecimal.valueOf(newPrice))
                .vatRate(currentService.getVatRate())
                .currency(currentService.getCurrency())
                .pricingUnit(currentService.getPricingUnit())
                .isActive(currentService.getIsActive())
                .minPriceThreshold(currentService.getMinPriceThreshold())
//                .relations(new ArrayList<>())
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/services/" + serviceId)
                .then()
                .extract()
                .response();

        context.setLastResponse(response);
    }

    @When("I delete the service")
    public void iDeleteTheService() {
        Long serviceId = context.getCreatedId("service");
        assertThat(serviceId).as("Service ID should exist").isNotNull();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .delete("/services/" + serviceId)
                .then()
                .extract()
                .response();

        context.setLastResponse(response);
    }

    @When("I list all services for account {long}")
    public void iListAllServicesForAccount(Long accountId) {
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .queryParam("accountId", accountId)
                .when()
                .get("/services")
                .then()
                .extract()
                .response();

        context.setLastResponse(response);
    }

    @And("the response should contain the service with name {string}")
    public void theResponseShouldContainTheServiceWithName(String expectedName) {
        Response response = context.getLastResponse();
        ServiceResponse serviceResponse = response.as(ServiceResponse.class);

        assertThat(serviceResponse.getName())
                .as("Service name should match")
                .isEqualTo(expectedName);
        assertThat(serviceResponse.getId())
                .as("Service should have an ID")
                .isNotNull();

        context.put("lastService", serviceResponse);
    }

//    @And("the service should have {int} related service(s)")
//    public void theServiceShouldHaveRelatedServices(int expectedCount) {
//        Response response = context.getLastResponse();
//        ServiceResponse serviceResponse = response.as(ServiceResponse.class);
//
//        assertThat(serviceResponse.getRelatedServices())
//                .as("Service should have related services")
//                .hasSize(expectedCount);
//    }

    @And("the response should contain a list of services")
    public void theResponseShouldContainAListOfServices() {
        Response response = context.getLastResponse();
        ServiceResponse[] services = response.as(ServiceResponse[].class);

        assertThat(services)
                .as("Response should contain services array")
                .isNotNull();
    }
}
