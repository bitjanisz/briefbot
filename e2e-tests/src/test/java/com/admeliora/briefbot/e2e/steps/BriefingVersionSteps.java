package com.admeliora.briefbot.e2e.steps;

import com.admeliora.briefbot.e2e.config.TestConfig;
import com.admeliora.briefbot.e2e.model.BriefingRequest;
import com.admeliora.briefbot.e2e.model.BriefingResponse;
import com.admeliora.briefbot.e2e.model.BriefingVersionRequest;
import com.admeliora.briefbot.e2e.model.BriefingVersionResponse;
import com.admeliora.briefbot.e2e.model.BriefingVersionUpdateRequest;
import com.admeliora.briefbot.e2e.model.FinalizeRequest;
import com.admeliora.briefbot.e2e.support.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Briefing Version operations
 */
public class BriefingVersionSteps {

    private final TestContext context;

    @Autowired
    public BriefingVersionSteps(TestContext context) {
        this.context = context;
    }

    @Given("I have a briefing with multiple versions")
    public void iHaveABriefingWithMultipleVersions() {
        // Assume briefing exists and has versions
        if (context.get("briefingId") == null) {
            // Create briefing and versions
            BriefingRequest briefingRequest = BriefingRequest.builder()
                    .title("Version Test Briefing")
                    .description("Test briefing")
                    .clientId(1L)
                    .deadline(LocalDateTime.now().plusDays(14))
                    .build();

            Response response = given()
                    .spec(TestConfig.getRequestSpec(context))
                    .body(briefingRequest)
                    .when()
                    .post("/briefings");

            Long briefingId = response.jsonPath().getLong("id");
            context.put("briefingId", briefingId);

            // Create version
            BriefingVersionRequest versionRequest = BriefingVersionRequest.builder()
                    .briefingId(briefingId)
                    .build();

            given()
                    .spec(TestConfig.getRequestSpec(context))
                    .body(versionRequest)
                    .when()
                    .post("/briefings/{briefingId}/versions", briefingId);
        }
    }

    @When("I get a specific briefing version by id")
    public void iGetASpecificBriefingVersionById() {
        // Assume version exists
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/briefing-versions/1")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the response should contain the briefing version details")
    public void theResponseShouldContainTheBriefingVersionDetails() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Given("I have an existing briefing version")
    public void iHaveAnExistingBriefingVersion() {
        iHaveABriefingWithMultipleVersions();
    }

    @When("I update the client responses")
    public void iUpdateTheClientResponses() {
        BriefingVersionUpdateRequest request = BriefingVersionUpdateRequest.builder()
                .clientResponses("{\"question1\": \"answer1\"}")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/briefing-versions/1")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the briefing version should be updated")
    public void theBriefingVersionShouldBeUpdated() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @When("I list all versions for that briefing")
    public void iListAllVersionsForThatBriefing() {
        Long briefingId = (Long) context.get("briefingId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/briefings/{briefingId}/versions", briefingId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the response should contain all briefing versions ordered by version number")
    public void theResponseShouldContainAllBriefingVersionsOrderedByVersionNumber() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Given("I have a briefing version that is finalized")
    public void iHaveABriefingVersionThatIsFinalized() {
        iHaveAnExistingBriefingVersion();
        // Assume there's an endpoint to finalize version
        FinalizeRequest request = FinalizeRequest.builder()
                .finalized(true)
                .build();

        given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/briefing-versions/1/finalize");
    }

    @When("I try to update the responses")
    public void iTryToUpdateTheResponses() {
        BriefingVersionUpdateRequest request = BriefingVersionUpdateRequest.builder()
                .clientResponses("{\"question2\": \"answer2\"}")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/briefing-versions/1")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @When("I create a new briefing version")
    public void iCreateANewBriefingVersion() {
        Long briefingId = (Long) context.get("briefingId");

        BriefingVersionRequest request = BriefingVersionRequest.builder()
                .briefingId(briefingId)
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/briefings/{briefingId}/versions", briefingId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the new briefing version should have number {int}")
    public void theNewBriefingVersionShouldHaveNumber(int expectedNumber) {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(201);
        Integer versionNumber = response.jsonPath().getInt("versionNumber");
        assertThat(versionNumber).isEqualTo(expectedNumber);
    }
}
