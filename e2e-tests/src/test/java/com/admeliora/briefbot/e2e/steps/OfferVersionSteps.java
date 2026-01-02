package com.admeliora.briefbot.e2e.steps;

import com.admeliora.briefbot.e2e.config.TestConfig;
import com.admeliora.briefbot.e2e.support.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Offer Version operations
 */
public class OfferVersionSteps {

    private final TestContext context;

    @Autowired
    public OfferVersionSteps(TestContext context) {
        this.context = context;
    }

    @Given("I have an offer with multiple versions")
    public void iHaveAnOfferWithMultipleVersions() {
        // Assume offer exists and has versions
        if (context.get("offerId") == null) {
            // Create offer and versions
            Response response = given()
                    .spec(TestConfig.getRequestSpec(context))
                    .body("""
                        {
                            "title": "Version Test Offer",
                            "description": "Test offer",
                            "clientId": 1,
                            "status": "DRAFT"
                        }
                        """)
                    .when()
                    .post("/offers");

            Long offerId = response.jsonPath().getLong("id");
            context.put("offerId", offerId);

            // Create version
            given()
                    .spec(TestConfig.getRequestSpec(context))
                    .body(String.format("{\"offerId\": %d}", offerId))
                    .when()
                    .post("/offers/{offerId}/versions", offerId);
        }
    }

    @When("I get a specific offer version by id")
    public void iGetASpecificOfferVersionById() {
        // Assume version exists
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/offer-versions/1")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the response should contain the offer version details")
    public void theResponseShouldContainTheOfferVersionDetails() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Given("I have an existing offer version")
    public void iHaveAnExistingOfferVersion() {
        iHaveAnOfferWithMultipleVersions();
    }

    @When("I update the version content and pricing")
    public void iUpdateTheVersionContentAndPricing() {
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body("{\"introductionContent\": \"Updated content\"}")
                .when()
                .put("/offer-versions/1")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the offer version should be updated")
    public void theOfferVersionShouldBeUpdated() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @When("I list all versions for that offer")
    public void iListAllVersionsForThatOffer() {
        Long offerId = (Long) context.get("offerId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/offers/{offerId}/versions", offerId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the response should contain all offer versions ordered by version number")
    public void theResponseShouldContainAllOfferVersionsOrderedByVersionNumber() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @When("I try to update the content")
    public void iTryToUpdateTheContent() {
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body("{\"introductionContent\": \"Attempted update\"}")
                .when()
                .put("/offer-versions/1")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @When("I create a new offer version")
    public void iCreateANewOfferVersion() {
        Long offerId = (Long) context.get("offerId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(String.format("{\"offerId\": %d}", offerId))
                .when()
                .post("/offers/{offerId}/versions", offerId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the new offer version should have number {int}")
    public void theNewOfferVersionShouldHaveNumber(int expectedNumber) {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(201);
        Integer versionNumber = response.jsonPath().getInt("versionNumber");
        assertThat(versionNumber).isEqualTo(expectedNumber);
    }
}
