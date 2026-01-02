package com.admeliora.briefbot.e2e.steps;

import com.admeliora.briefbot.e2e.config.TestConfig;
import com.admeliora.briefbot.e2e.model.ClientRequest;
import com.admeliora.briefbot.e2e.model.OfferRequest;
import com.admeliora.briefbot.e2e.model.OfferResponse;
import com.admeliora.briefbot.e2e.model.StatusUpdateRequest;
import com.admeliora.briefbot.e2e.support.TestContext;
import io.cucumber.java.en.And;
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
 * Step definitions for Offer operations
 */
public class OfferSteps {

    private final TestContext context;

    @Autowired
    public OfferSteps(TestContext context) {
        this.context = context;
    }

    @Given("I have an existing client")
    public void iHaveAnExistingClient() {
        ClientRequest request = ClientRequest.builder()
                .accountId(TestConfig.getDefaultAccountId())
                .name("Test Client")
                .email("test@client.com")
                .industry("industry")
                .companyName("Test Company")
                .industry("IT")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/clients");

        assertThat(response.getStatusCode()).isEqualTo(201);
        Long clientId = response.jsonPath().getLong("id");
        context.put("clientId", clientId);
    }

    @When("I create an offer for that client with title {string}")
    public void iCreateAnOfferForThatClientWithTitle(String title) {
        Long clientId = (Long) context.get("clientId");

        OfferRequest request = OfferRequest.builder()
                .title(title)
                .description("Test offer description")
                .clientId(clientId)
                .status("DRAFT")
                .validUntil(LocalDateTime.now().plusDays(30))
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/offers")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the offer should be created with status {string}")
    public void theOfferShouldBeCreatedWithStatus(String status) {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(201);

        OfferResponse offer = response.as(OfferResponse.class);
        assertThat(offer.getId()).isNotNull();
        assertThat(offer.getStatus()).isEqualTo(status);

        context.put("offerId", offer.getId());
    }

    @Given("I have an existing offer")
    public void iHaveAnExistingOffer() {
        if (context.get("offerId") == null) {
            iHaveAnExistingClient();
            iCreateAnOfferForThatClientWithTitle("Test Offer");
            assertThat(context.getLastResponse().getStatusCode()).isEqualTo(201);
        }
    }

    @When("I get the offer by id")
    public void iGetTheOfferById() {
        Long offerId = (Long) context.get("offerId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/offers/{offerId}", offerId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the response should contain the offer details")
    public void theResponseShouldContainTheOfferDetails() {
        Response response = context.getLastResponse();
        OfferResponse offer = response.as(OfferResponse.class);
        assertThat(offer.getId()).isNotNull();
        assertThat(offer.getTitle()).isNotNull();
    }

    @When("I update the offer status to {string}")
    public void iUpdateTheOfferStatusTo(String status) {
        Long offerId = (Long) context.get("offerId");

        StatusUpdateRequest request = StatusUpdateRequest.builder()
                .status(status)
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/offers/{offerId}/status", offerId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the offer status should be {string}")
    public void theOfferStatusShouldBe(String status) {
        Response response = context.getLastResponse();
        OfferResponse offer = response.as(OfferResponse.class);
        assertThat(offer.getStatus()).isEqualTo(status);
    }

    @When("I list all offers for account 1")
    public void iListAllOffersForAccount() {
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/offers?accountId=1")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the response should contain a list of offers")
    public void theResponseShouldContainAListOfOffers() {
        Response response = context.getLastResponse();
        List<OfferResponse> offers = response.jsonPath().getList("", OfferResponse.class);
        assertThat(offers).isNotNull();
    }

    @When("I create an offer without specifying a client")
    public void iCreateAnOfferWithoutSpecifyingAClient() {
        OfferRequest request = OfferRequest.builder()
                .title("Offer Without Client")
                .description("Test description")
                .status("DRAFT")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/offers")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Given("I have an offer in status {string}")
    public void iHaveAnOfferInStatus(String status) {
        iHaveAnExistingClient();
        iCreateAnOfferForThatClientWithTitle("Status Test Offer");
        iUpdateTheOfferStatusTo(status);
    }

    @When("I try to update status back to {string}")
    public void iTryToUpdateStatusBackTo(String status) {
        Long offerId = (Long) context.get("offerId");

        StatusUpdateRequest request = StatusUpdateRequest.builder()
                .status(status)
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/offers/{offerId}/status", offerId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }
}
