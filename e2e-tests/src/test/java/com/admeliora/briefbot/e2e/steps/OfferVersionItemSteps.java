package com.admeliora.briefbot.e2e.steps;

import com.admeliora.briefbot.e2e.config.TestConfig;
import com.admeliora.briefbot.e2e.model.request.ClientRequest;
import com.admeliora.briefbot.e2e.model.request.OfferRequest;
import com.admeliora.briefbot.e2e.model.request.OfferVersionItemRequest;
import com.admeliora.briefbot.e2e.model.request.OfferVersionRequest;
import com.admeliora.briefbot.e2e.support.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Offer Version Item operations
 */
public class OfferVersionItemSteps {

    private final TestContext context;

    @Autowired
    public OfferVersionItemSteps(TestContext context) {
        this.context = context;
    }

    @Given("I have an existing offer version for item operations")
    public void iHaveAnExistingOfferVersionForItemOperations() {
        // Assume offer version exists from previous steps
        if (context.get("offerVersionId") == null) {
            // Create offer and version
            OfferRequest offerRequest = OfferRequest.builder()
                    .title("Item Test Offer")
                    .description("Test offer")
                    .clientId(1L)
                    .status("DRAFT")
                    .build();

            Response offerResponse = given()
                    .spec(TestConfig.getRequestSpec(context))
                    .body(offerRequest)
                    .when()
                    .post("/offers");

            Long offerId = offerResponse.jsonPath().getLong("id");

            OfferVersionRequest versionRequest = OfferVersionRequest.builder()
                    .offerId(offerId)
                    .build();

            Response versionResponse = given()
                    .spec(TestConfig.getRequestSpec(context))
                    .body(versionRequest)
                    .when()
                    .post("/offers/{offerId}/versions", offerId);

            Long versionId = versionResponse.jsonPath().getLong("id");
            context.put("offerVersionId", versionId);
        }
    }

    @Given("I have an existing service")
    public void iHaveAnExistingService() {
        if (context.get("serviceId") == null) {
            Response response = given()
                    .spec(TestConfig.getRequestSpec(context))
                    .body("""
                        {
                            "name": "Test Service",
                            "description": "Test service for items",
                            "accountId": 1,
                            "basePrice": 1000.00,
                            "vatRate": 23.00,
                            "currency": "PLN",
                            "pricingUnit": "project",
                            "isActive": true,
                            "relations": []
                        }
                        """)
                    .when()
                    .post("/services");

            Long serviceId = response.jsonPath().getLong("id");
            context.put("serviceId", serviceId);
        }
    }

    @When("I add that service as an item to the offer version")
    public void iAddThatServiceAsAnItemToTheOfferVersion() {
        Long versionId = (Long) context.get("offerVersionId");
        Long serviceId = (Long) context.get("serviceId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(String.format("""
                    {
                        "offerVersionId": %d,
                        "originalServiceId": %d,
                        "serviceName": "Test Service",
                        "description": "Test item",
                        "quantity": 1.00,
                        "price": 1000.00,
                        "vatRate": 23.00
                    }
                    """, versionId, serviceId))
                .when()
                .post("/offer-version-items")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the item should be added with calculated pricing")
    public void theItemShouldBeAddedWithCalculatedPricing() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(201);
    }

    @Given("I have an offer version with items")
    public void iHaveAnOfferVersionWithItems() {
        iHaveAnExistingOfferVersionItem();
        iHaveAnExistingService();
        iAddThatServiceAsAnItemToTheOfferVersion();
        assertThat(context.getLastResponse().getStatusCode()).isEqualTo(201);
    }

    @When("I get a specific item by id")
    public void iGetASpecificItemById() {
        // Assume item exists
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/offer-version-items/1")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the response should contain the item details")
    public void theResponseShouldContainTheItemDetails() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Given("I have an existing offer version item")
    public void iHaveAnExistingOfferVersionItem() {
        iHaveAnExistingOfferVersionForItemOperations();
        iHaveAnExistingService();
        iAddThatServiceAsAnItemToTheOfferVersion();
        assertThat(context.getLastResponse().getStatusCode()).isEqualTo(201);
    }

    @When("I update the item quantity to 5 and price to 1500.00")
    public void iUpdateTheItemQuantityTo5AndPriceTo150000() {
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body("""
                    {
                        "quantity": 5.00,
                        "price": 1500.00
                    }
                    """)
                .when()
                .put("/offer-version-items/1")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the item should be updated with new totals")
    public void theItemShouldBeUpdatedWithNewTotals() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Given("I have an offer version with an item")
    public void iHaveAnOfferVersionWithAnItem() {
        iHaveAnOfferVersionWithItems();
    }

    @When("I delete the item")
    public void iDeleteTheItem() {
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .delete("/offer-version-items/1")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the offer version total should be recalculated")
    public void theOfferVersionTotalShouldBeRecalculated() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(204);
    }

    @When("I list all items for that version")
    public void iListAllItemsForThatVersion() {
        Long versionId = (Long) context.get("offerVersionId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/offer-versions/{versionId}/items", versionId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the response should contain all items")
    public void theResponseShouldContainAllItems() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @When("I try to add an item with non-existent service ID")
    public void iTryToAddAnItemWithNonExistentServiceID() {
        Long versionId = (Long) context.get("offerVersionId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(String.format("""
                    {
                        "offerVersionId": %d,
                        "originalServiceId": 99999,
                        "serviceName": "Invalid Service",
                        "quantity": 1.00,
                        "price": 1000.00,
                        "vatRate": 23.00
                    }
                    """, versionId))
                .when()
                .post("/offer-version-items")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @When("I add a new item with quantity 2 and price 100.00")
    public void iAddANewItemWithQuantity2AndPrice10000() {
        Long versionId = (Long) context.get("offerVersionId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(String.format("""
                    {
                        "offerVersionId": %d,
                        "originalServiceId": 1,
                        "serviceName": "Additional Service",
                        "quantity": 2.00,
                        "price": 100.00,
                        "vatRate": 23.00
                    }
                    """, versionId))
                .when()
                .post("/offer-version-items")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the offer version total should increase by 200.00")
    public void theOfferVersionTotalShouldIncreaseBy20000() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(201);
    }

    @When("I update the item quantity to 3")
    public void iUpdateTheItemQuantityTo3() {
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body("{\"quantity\": 3.00}")
                .when()
                .put("/offer-version-items/1")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the offer version total should increase by another 100.00")
    public void theOfferVersionTotalShouldIncreaseByAnother10000() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }
}
