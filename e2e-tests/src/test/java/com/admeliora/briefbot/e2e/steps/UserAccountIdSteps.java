package com.admeliora.briefbot.e2e.steps;

import com.admeliora.briefbot.e2e.config.TestConfig;
import com.admeliora.briefbot.e2e.model.request.RoleUpdateRequest;
import com.admeliora.briefbot.e2e.model.request.UserAccountRequest;
import com.admeliora.briefbot.e2e.support.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for User Account ID Validation operations
 */
public class UserAccountIdSteps {

    private final TestContext context;

    @Autowired
    public UserAccountIdSteps(TestContext context) {
        this.context = context;
    }

    @Given("I have a user account relationship")
    public void iHaveAUserAccountRelationship() {
        // Create a user account relationship
        UserAccountRequest request = UserAccountRequest.builder()
                .userId(10L)
                .accountId(1L)
                .role("MEMBER")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/user-accounts");

        assertThat(response.getStatusCode()).isEqualTo(200);
        context.put("userId", 10L);
        context.put("accountId", 1L);
    }

    @When("I try to create another relationship with same user and account")
    public void iTryToCreateAnotherRelationshipWithSameUserAndAccount() {
        Long userId = (Long) context.get("userId");
        Long accountId = (Long) context.get("accountId");

        UserAccountRequest request = UserAccountRequest.builder()
                .userId(userId)
                .accountId(accountId)
                .role("ADMIN")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/user-accounts")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Given("I have a user in account A for ID validation")
    public void iHaveAUserInAccountAForIdValidation() {
        // Create user in account A
        UserAccountRequest request = UserAccountRequest.builder()
                .userId(11L)
                .accountId(2L)
                .role("MEMBER")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/user-accounts");

        assertThat(response.getStatusCode()).isEqualTo(200);
        context.put("accountAUserId", 11L);
    }

    @When("I add the same user to account B for ID validation")
    public void iAddTheSameUserToAccountBForIdValidation() {
        Long userId = (Long) context.get("accountAUserId");

        UserAccountRequest request = UserAccountRequest.builder()
                .userId(userId)
                .accountId(3L)
                .role("MEMBER")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/user-accounts")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the user should be in both accounts for ID validation")
    public void theUserShouldBeInBothAccountsForIdValidation() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Given("I have account with user X for ID validation")
    public void iHaveAccountWithUserXForIdValidation() {
        // Create account with user X
        UserAccountRequest request = UserAccountRequest.builder()
                .userId(12L)
                .accountId(4L)
                .role("MEMBER")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/user-accounts");

        assertThat(response.getStatusCode()).isEqualTo(200);
        context.put("userXId", 12L);
    }

    @When("I add user Y to the same account for ID validation")
    public void iAddUserYToTheSameAccountForIdValidation() {
        UserAccountRequest request = UserAccountRequest.builder()
                .userId(13L)
                .accountId(4L)
                .role("MEMBER")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/user-accounts")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("both users should be in the account for ID validation")
    public void bothUsersShouldBeInTheAccountForIdValidation() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }
}
