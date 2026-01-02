package com.admeliora.briefbot.e2e.steps;

import com.admeliora.briefbot.e2e.config.TestConfig;
import com.admeliora.briefbot.e2e.model.ClientRequest;
import com.admeliora.briefbot.e2e.model.ClientResponse;
import com.admeliora.briefbot.e2e.support.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Client CRUD operations
 */
public class ClientSteps {

    private final TestContext context;

    @Autowired
    public ClientSteps(TestContext context) {
        this.context = context;
    }

    @When("I create a client with name {string} and email {string}")
    public void iCreateAClientWithNameAndEmail(String name, String email) {
        ClientRequest request = ClientRequest.builder()
                .accountId(TestConfig.getDefaultAccountId())
                .name(name)
                .email(email)
                .companyName("Test Company")
                .industry("IT")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/clients")
                .then()
                .extract()
                .response();

        context.setLastResponse(response);

        if (response.getStatusCode() == 201) {
            ClientResponse clientResponse = response.as(ClientResponse.class);
            context.setCreatedId("client", clientResponse.getId());
        }
    }

    @When("I get the client by id")
    public void iGetTheClientById() {
        Long clientId = context.getCreatedId("client");
        assertThat(clientId).as("Client ID should exist").isNotNull();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/clients/" + clientId)
                .then()
                .extract()
                .response();

        context.setLastResponse(response);
    }

    @When("I update the client name to {string}")
    public void iUpdateTheClientNameTo(String newName) {
        Long clientId = context.getCreatedId("client");
        assertThat(clientId).as("Client ID should exist").isNotNull();

        ClientRequest request = ClientRequest.builder()
                .accountId(TestConfig.getDefaultAccountId())
                .name(newName)
                .email("updated@example.com")
                .companyName("Updated Company")
                .industry("IT")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/clients/" + clientId)
                .then()
                .extract()
                .response();

        context.setLastResponse(response);
    }

    @When("I delete the client")
    public void iDeleteTheClient() {
        Long clientId = context.getCreatedId("client");
        assertThat(clientId).as("Client ID should exist").isNotNull();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .delete("/clients/" + clientId)
                .then()
                .extract()
                .response();

        context.setLastResponse(response);
    }

    @When("I list all clients for account {long}")
    public void iListAllClientsForAccount(Long accountId) {
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .queryParam("accountId", accountId)
                .when()
                .get("/clients")
                .then()
                .extract()
                .response();

        context.setLastResponse(response);
    }

    @And("the response should contain the client with name {string}")
    public void theResponseShouldContainTheClientWithName(String expectedName) {
        Response response = context.getLastResponse();
        ClientResponse clientResponse = response.as(ClientResponse.class);

        assertThat(clientResponse.getName())
                .as("Client name should match")
                .isEqualTo(expectedName);
        assertThat(clientResponse.getId())
                .as("Client should have an ID")
                .isNotNull();
    }

    @And("the response should contain a list of clients")
    public void theResponseShouldContainAListOfClients() {
        Response response = context.getLastResponse();
        ClientResponse[] clients = response.as(ClientResponse[].class);

        assertThat(clients)
                .as("Response should contain clients array")
                .isNotNull();
    }

    @And("I create a sample client")
    public void iCreateASampleClient() {
        var radom = RandomStringUtils.insecure().nextAlphabetic(5);
        String randomEmail = "client+" + radom + "@example.com";
        iCreateAClientWithNameAndEmail("Sample " + radom + " Client ", randomEmail);
    }
}

