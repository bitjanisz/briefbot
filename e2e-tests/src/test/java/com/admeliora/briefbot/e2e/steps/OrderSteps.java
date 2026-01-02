package com.admeliora.briefbot.e2e.steps;

import com.admeliora.briefbot.e2e.config.TestConfig;
import com.admeliora.briefbot.e2e.model.ClientRequest;
import com.admeliora.briefbot.e2e.model.OfferRequest;
import com.admeliora.briefbot.e2e.model.OrderRequest;
import com.admeliora.briefbot.e2e.model.OrderResponse;
import com.admeliora.briefbot.e2e.model.StatusUpdateRequest;
import com.admeliora.briefbot.e2e.support.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Order operations
 */
public class OrderSteps {

    private final TestContext context;

    @Autowired
    public OrderSteps(TestContext context) {
        this.context = context;
    }

    @Given("I have an accepted offer")
    public void iHaveAnAcceptedOffer() {
        // Create client and offer, then accept it
        ClientRequest clientRequest = ClientRequest.builder()
                .accountId(TestConfig.getDefaultAccountId())
                .name("Order Test Client")
                .email("order@test.com")
                .companyName("Test Company")
                .industry("IT")
                .build();

        Response clientResponse = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(clientRequest)
                .when()
                .post("/clients");

        Long clientId = clientResponse.jsonPath().getLong("id");

        OfferRequest offerRequest = OfferRequest.builder()
                .title("Order Test Offer")
                .description("Test offer for order")
                .clientId(clientId)
                .status("DRAFT")
                .build();

        Response offerResponse = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(offerRequest)
                .when()
                .post("/offers");

        Long offerId = offerResponse.jsonPath().getLong("id");

        // Accept the offer
        StatusUpdateRequest statusRequest = StatusUpdateRequest.builder()
                .status("ACCEPTED")
                .build();

        given()
                .spec(TestConfig.getRequestSpec(context))
                .body(statusRequest)
                .when()
                .put("/offers/{offerId}/status", offerId);

        context.put("offerId", offerId);
    }

    @When("I create an order from that offer")
    public void iCreateAnOrderFromThatOffer() {
        Long offerId = (Long) context.get("offerId");

        OrderRequest request = OrderRequest.builder()
                .offerId(offerId)
                .notes("Test order notes")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/orders")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the order should be created with status {string}")
    public void theOrderShouldBeCreatedWithStatus(String status) {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(201);

        OrderResponse order = response.as(OrderResponse.class);
        assertThat(order.getId()).isNotNull();
        assertThat(order.getStatus()).isEqualTo(status);

        context.put("orderId", order.getId());
    }

    @Given("I have an existing order")
    public void iHaveAnExistingOrder() {
        if (context.get("orderId") == null) {
            iHaveAnAcceptedOffer();
            iCreateAnOrderFromThatOffer();
            assertThat(context.getLastResponse().getStatusCode()).isEqualTo(201);
        }
    }

    @When("I get the order by id")
    public void iGetTheOrderById() {
        Long orderId = (Long) context.get("orderId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/orders/{orderId}", orderId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @When("I update the order status to {string}")
    public void iUpdateTheOrderStatusTo(String status) {
        Long orderId = (Long) context.get("orderId");

        StatusUpdateRequest request = StatusUpdateRequest.builder()
                .status(status)
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/orders/{orderId}/status", orderId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the order status should be {string}")
    public void theOrderStatusShouldBe(String status) {
        Response response = context.getLastResponse();
        OrderResponse order = response.as(OrderResponse.class);
        assertThat(order.getStatus()).isEqualTo(status);
    }

    @When("I list all orders for account 1")
    public void iListAllOrdersForAccount() {
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/orders?accountId=1")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the response should contain a list of orders")
    public void theResponseShouldContainAListOfOrders() {
        Response response = context.getLastResponse();
        List<OrderResponse> orders = response.jsonPath().getList("", OrderResponse.class);
        assertThat(orders).isNotNull();
    }

    @Given("I have an offer in status {string} for order")
    public void iHaveAnOfferInStatusForOrder(String status) {
        // Create offer with specific status
        ClientRequest clientRequest = ClientRequest.builder()
                .accountId(1L)
                .name("Status Test Client")
                .email("status@test.com")
                .companyName("Test Company")
                .industry("IT")
                .build();

        Response clientResponse = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(clientRequest)
                .when()
                .post("/clients");

        Long clientId = clientResponse.jsonPath().getLong("id");

        OfferRequest offerRequest = OfferRequest.builder()
                .title("Status Test Offer")
                .description("Test offer")
                .clientId(clientId)
                .status(status)
                .build();

        Response offerResponse = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(offerRequest)
                .when()
                .post("/offers");

        Long offerId = offerResponse.jsonPath().getLong("id");
        context.put("offerId", offerId);
    }

    @When("I try to create an order from that offer")
    public void iTryToCreateAnOrderFromThatOffer() {
        Long offerId = (Long) context.get("offerId");

        OrderRequest request = OrderRequest.builder()
                .offerId(offerId)
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/orders")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Given("I have an order in status {string}")
    public void iHaveAnOrderInStatus(String status) {
        iHaveAnAcceptedOffer();
        iCreateAnOrderFromThatOffer();
        iUpdateTheOrderStatusTo(status);
    }

    @When("I try to update the status")
    public void iTryToUpdateTheStatus() {
        Long orderId = (Long) context.get("orderId");

        StatusUpdateRequest request = StatusUpdateRequest.builder()
                .status("DRAFT")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/orders/{orderId}/status", orderId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }
}
