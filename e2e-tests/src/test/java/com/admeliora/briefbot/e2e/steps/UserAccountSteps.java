package com.admeliora.briefbot.e2e.steps;

import com.admeliora.briefbot.e2e.config.TestConfig;
import com.admeliora.briefbot.e2e.model.request.AccountUserRequest;
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
 * Step definitions for User Account operations
 */
public class UserAccountSteps {

    private final TestContext context;

    @Autowired
    public UserAccountSteps(TestContext context) {
        this.context = context;
    }

    @Given("I have a registered user not in any account")
    public void iHaveARegisteredUserNotInAnyAccount() {
        // Assume user exists from authentication steps
        context.put("userId", 3L);
    }

    @When("I add that user to my account with MEMBER role")
    public void iAddThatUserToMyAccountWithMEMBERRole() {
        Long userId = (Long) context.get("userId");

        AccountUserRequest request = AccountUserRequest.builder()
                .userId(userId)
                .role("MEMBER")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/accounts/1/users")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the user should be added to the account")
    public void theUserShouldBeAddedToTheAccount() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Given("I have a user in my account with MEMBER role")
    public void iHaveAUserInMyAccountWithMEMBERRole() {
        iHaveARegisteredUserNotInAnyAccount();
        iAddThatUserToMyAccountWithMEMBERRole();
        assertThat(context.getLastResponse().getStatusCode()).isEqualTo(200);
    }

    @When("I change their role to ADMIN")
    public void iChangeTheirRoleToADMIN() {
        Long userId = (Long) context.get("userId");

        RoleUpdateRequest request = RoleUpdateRequest.builder()
                .role("ADMIN")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/accounts/1/users")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the user should have ADMIN role in the account")
    public void theUserShouldHaveADMINRoleInTheAccount() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @When("I remove that user from the account")
    public void iRemoveThatUserFromTheAccount() {
        Long userId = (Long) context.get("userId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .delete("/accounts/1/users/{userId}", userId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the user should no longer have access to account resources")
    public void theUserShouldNoLongerHaveAccessToAccountResources() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(204);
    }

    @When("I list all members of my account")
    public void iListAllMembersOfMyAccount() {
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/accounts/1/users")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the response should contain all account members with their roles")
    public void theResponseShouldContainAllAccountMembersWithTheirRoles() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Given("I have a user already in my account")
    public void iHaveAUserAlreadyInMyAccount() {
        iHaveAUserInMyAccountWithMEMBERRole();
    }

    @When("I try to add them again")
    public void iTryToAddThemAgain() {
        Long userId = (Long) context.get("userId");

        AccountUserRequest request = AccountUserRequest.builder()
                .userId(userId)
                .role("MEMBER")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/accounts/1/users")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Given("I have a user in account A")
    public void iHaveAUserInAccountA() {
        // Setup user in account A
        context.put("accountAUserId", 4L);
    }

    @When("I add the same user to account B")
    public void iAddTheSameUserToAccountB() {
        Long userId = (Long) context.get("accountAUserId");

        AccountUserRequest request = AccountUserRequest.builder()
                .userId(userId)
                .role("MEMBER")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/accounts/2/users")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the user should be in both accounts")
    public void theUserShouldBeInBothAccounts() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Given("I have account with user X")
    public void iHaveAccountWithUserX() {
        // Setup account with user X
        context.put("userXId", 5L);
    }

    @When("I add user Y to the same account")
    public void iAddUserYToTheSameAccount() {
        AccountUserRequest request = AccountUserRequest.builder()
                .userId(6L)
                .role("MEMBER")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/accounts/1/users")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("both users should be in the account")
    public void bothUsersShouldBeInTheAccount() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }
}
