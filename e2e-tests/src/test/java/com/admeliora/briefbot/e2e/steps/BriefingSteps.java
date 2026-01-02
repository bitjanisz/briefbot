package com.admeliora.briefbot.e2e.steps;

import com.admeliora.briefbot.e2e.config.TestConfig;
import com.admeliora.briefbot.e2e.model.BriefingRequest;
import com.admeliora.briefbot.e2e.model.BriefingResponse;
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
 * Step definitions for Briefing operations
 */
public class BriefingSteps {

    private final TestContext context;

    @Autowired
    public BriefingSteps(TestContext context) {
        this.context = context;
    }

    @When("I create a briefing for that client with title {string}")
    public void iCreateABriefingForThatClientWithTitle(String title) {
        Long clientId = (Long) context.get("clientId");

        BriefingRequest request = BriefingRequest.builder()
                .title(title)
                .description("Test briefing description")
                .clientId(clientId)
                .deadline(LocalDateTime.now().plusDays(14))
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/briefings")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the briefing should be created")
    public void theBriefingShouldBeCreated() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(201);

        BriefingResponse briefing = response.as(BriefingResponse.class);
        assertThat(briefing.getId()).isNotNull();

        context.put("briefingId", briefing.getId());
    }

    @Given("I have an existing briefing")
    public void iHaveAnExistingBriefing() {
        if (context.get("briefingId") == null) {
            // Assume client exists from previous steps
            iCreateABriefingForThatClientWithTitle("Test Briefing");
            assertThat(context.getLastResponse().getStatusCode()).isEqualTo(201);
        }
    }

    @When("I get the briefing by id")
    public void iGetTheBriefingById() {
        Long briefingId = (Long) context.get("briefingId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/briefings/{briefingId}", briefingId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the response should contain the briefing details")
    public void theResponseShouldContainTheBriefingDetails() {
        Response response = context.getLastResponse();
        BriefingResponse briefing = response.as(BriefingResponse.class);
        assertThat(briefing.getId()).isNotNull();
        assertThat(briefing.getTitle()).isNotNull();
    }

    @When("I update the briefing requirements")
    public void iUpdateTheBriefingRequirements() {
        Long briefingId = (Long) context.get("briefingId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body("{\"description\": \"Updated briefing requirements\"}")
                .when()
                .put("/briefings/{briefingId}", briefingId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the briefing should be updated")
    public void theBriefingShouldBeUpdated() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @When("I list all briefings for account 1")
    public void iListAllBriefingsForAccount() {
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/briefings?accountId=1")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the response should contain a list of briefings")
    public void theResponseShouldContainAListOfBriefings() {
        Response response = context.getLastResponse();
        List<BriefingResponse> briefings = response.jsonPath().getList("", BriefingResponse.class);
        assertThat(briefings).isNotNull();
    }

    @When("I create a briefing without specifying a client")
    public void iCreateABriefingWithoutSpecifyingAClient() {
        BriefingRequest request = BriefingRequest.builder()
                .title("Briefing Without Client")
                .description("Test description")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/briefings")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Given("I have a briefing marked as finalized")
    public void iHaveABriefingMarkedAsFinalized() {
        iHaveAnExistingBriefing();
        // Assume there's an endpoint to finalize briefing
        Long briefingId = (Long) context.get("briefingId");
        given()
                .spec(TestConfig.getRequestSpec(context))
                .body("{\"finalized\": true}")
                .when()
                .put("/briefings/{briefingId}/finalize", briefingId);
    }

    @When("I try to update the briefing")
    public void iTryToUpdateTheBriefing() {
        Long briefingId = (Long) context.get("briefingId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body("{\"description\": \"Attempted update\"}")
                .when()
                .put("/briefings/{briefingId}", briefingId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }
}
