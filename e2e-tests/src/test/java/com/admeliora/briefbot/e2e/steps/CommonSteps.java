package com.admeliora.briefbot.e2e.steps;

import com.admeliora.briefbot.e2e.config.TestConfig;
import com.admeliora.briefbot.e2e.support.TestContext;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Common step definitions for all tests
 */
public class CommonSteps {

    private final TestContext context;

    @Autowired
    public CommonSteps(TestContext context) {
        this.context = context;
    }

    @Before
    public void setUp() {
        context.clear();
    }

    @Given("the API is available")
    public void theApiIsAvailable() {
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get(TestConfig.getActuatorUrl())
                .then()
                .extract()
                .response();

        assertThat(response.getStatusCode())
                .as("API should be available")
                .isIn(200);
    }

    @Given("I am authenticated")
    public void iAmAuthenticated() {
        // For now, skip authentication since it's hardcoded to accountId=1
        // In the future, add OAuth2 token handling here
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int expectedStatus) {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode())
                .as("Response status should be " + expectedStatus)
                .isEqualTo(expectedStatus);
    }

    @And("the response should contain error message {string}")
    public void theResponseShouldContainErrorMessage(String errorMessage) {
        Response response = context.getLastResponse();
        String responseBody = response.getBody().asString();

        assertThat(responseBody)
                .as("Response should contain error message: " + errorMessage)
                .containsIgnoringCase(errorMessage);
    }
}

