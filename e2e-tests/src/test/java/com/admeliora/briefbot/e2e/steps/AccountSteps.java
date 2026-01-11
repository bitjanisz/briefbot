package com.admeliora.briefbot.e2e.steps;

import com.admeliora.briefbot.e2e.config.TestConfig;
import com.admeliora.briefbot.e2e.model.request.AccountRequest;
import com.admeliora.briefbot.e2e.model.response.AccountResponse;
import com.admeliora.briefbot.e2e.support.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Account operations
 */
public class AccountSteps {

    private final TestContext context;

    @Autowired
    public AccountSteps(TestContext context) {
        this.context = context;
    }

    @When("I create an account with name {string}")
    public void iCreateAnAccountWithName(String name) {
        AccountRequest request = AccountRequest.builder()
                .name(name)
                .ownerId(TestConfig.getDefaultUserId())
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/accounts")
                .then()
                .extract()
                .response();

        context.setLastResponse(response);

        if (response.getStatusCode() == 201) {
            AccountResponse accountResponse = response.as(AccountResponse.class);
            context.setCreatedId("account", accountResponse.getId());
            context.put("lastAccount", accountResponse);
        } else {
            throw new IllegalStateException("Account creation failed with status: " + response.getStatusCode());
        }
    }

    @When("I get the created account")
    public void iGetTheCreatedAccount() {
        Long accountId = context.getCreatedId("account");
        assertThat(accountId).as("Account ID should exist").isNotNull();
        iGetAccountWithId(accountId);
    }

    @When("I get account with id {long}")
    public void iGetAccountWithId(Long accountId) {
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/accounts/" + accountId)
                .then()
                .extract()
                .response();

        context.setLastResponse(response);
    }

    @When("I list all accounts")
    public void iListAllAccounts() {
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/accounts")
                .then()
                .extract()
                .response();

        context.setLastResponse(response);
    }

    @And("the response should contain account details")
    public void theResponseShouldContainAccountDetails() {
        Response response = context.getLastResponse();
        assertThat((Object) response.jsonPath().get("id"))
                .as("Account should have an ID")
                .isNotNull();
        assertThat((Object) response.jsonPath().get("name"))
                .as("Account should have a name")
                .isNotNull();
    }

    @And("the response should contain a list of accounts")
    public void theResponseShouldContainAListOfAccounts() {
        Response response = context.getLastResponse();
        assertThat((Object) response.jsonPath().getList("$"))
                .as("Response should contain accounts array")
                .isNotNull();
    }

    @And("the response should contain account with data:")
    public void theResponseShouldContainAccountWithData(DataTable dataTable) {
        Response response = context.getLastResponse();
        AccountResponse accountResponse = response.as(AccountResponse.class);

        // Convert DataTable to Map for easier access
        Map<String, String> expectedData = dataTable.asMap(String.class, String.class);

        // Verify each field from the table
        expectedData.forEach((field, expectedValue) -> {
            switch (field.toLowerCase()) {
                case "name" -> assertThat(accountResponse.getName())
                        .as("Account name should match")
                        .isEqualTo(expectedValue);
                case "id" -> assertThat(accountResponse.getId())
                        .as("Account ID should match")
                        .isEqualTo(Long.parseLong(expectedValue));
                default -> throw new IllegalArgumentException("Unknown field: " + field);
            }
        });

        // Store for potential future use
        context.put("lastAccount", accountResponse);
    }
}

