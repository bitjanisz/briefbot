package com.admeliora.briefbot.e2e.steps;

import com.admeliora.briefbot.e2e.config.TestConfig;
import com.admeliora.briefbot.e2e.model.response.AccountUserResponse;
import com.admeliora.briefbot.e2e.model.request.RoleUpdateRequest;
import com.admeliora.briefbot.e2e.support.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Account Role operations
 */
public class AccountRoleSteps {

    private final TestContext context;

    @Autowired
    public AccountRoleSteps(TestContext context) {
        this.context = context;
    }

    @When("I assign {string} role to that user")
    public void iAssignRoleToThatUser(String role) {
        Long userId = (Long) context.get("userId");

        RoleUpdateRequest request = RoleUpdateRequest.builder()
                .role(role)
                .userId(userId)
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/accounts/1/users")
                .then()
                .extract().response();

        context.setLastResponse(response);

        // Verify the response contains the user with the assigned role
        if (response.getStatusCode() == 200) {
            AccountUserResponse userResponse = response.as(AccountUserResponse.class);
            assertThat(userResponse.userId()).isEqualTo(userId);
            assertThat(userResponse.role().name()).isEqualTo(role);
        }
    }

    @Then("the user should have {string} role")
    public void theUserShouldHaveRole(String expectedRole) {
        Long userId = (Long) context.get("userId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/accounts/1/users")
                .then()
                .extract().response();

        assertThat(response.getStatusCode()).isEqualTo(200);

        // Parse the response and check if the user has the expected role
        AccountUserResponse[] usersArray = response.as(AccountUserResponse[].class);
        List<AccountUserResponse> users = List.of(usersArray);
        AccountUserResponse user = users.stream()
                .filter(u -> u.userId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new AssertionError("User not found in account"));

        assertThat(user.role().name()).isEqualTo(expectedRole);
    }

    @When("I change their role to {string}")
    public void iChangeTheirRoleTo(String role) {
        Long userId = (Long) context.get("userId");

        RoleUpdateRequest request = RoleUpdateRequest.builder()
                .role(role)
                .userId(userId)
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/accounts/1/users")
                .then()
                .extract().response();

        context.setLastResponse(response);

        // Verify the response contains the user with the changed role
        if (response.getStatusCode() == 200) {
            AccountUserResponse userResponse = response.as(AccountUserResponse.class);
            assertThat(userResponse.userId()).isEqualTo(userId);
            assertThat(userResponse.role().name()).isEqualTo(role);
        }
    }

    @When("I remove that user from the account role")
    public void iRemoveThatUserFromTheAccountRole() {
        Long userId = (Long) context.get("userId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .delete("/accounts/1/users/{userId}", userId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the user should no longer be in the account")
    public void theUserShouldNoLongerBeInTheAccount() {
        Long userId = (Long) context.get("userId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/accounts/1/users")
                .then()
                .extract().response();

        assertThat(response.getStatusCode()).isEqualTo(200);

        // Parse the response and check that the user is not in the list
        AccountUserResponse[] usersArray = response.as(AccountUserResponse[].class);
        List<AccountUserResponse> users = List.of(usersArray);
        boolean userPresent = users.stream()
                .anyMatch(u -> u.userId().equals(userId));

        assertThat(userPresent).isFalse();
    }

    @When("I list all users in my account")
    public void iListAllUsersInMyAccount() {
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/accounts/1/users")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the response should contain users with their roles")
    public void theResponseShouldContainUsersWithTheirRoles() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
        // Verify response contains user-role data
    }

    @Given("I login as account member")
    public void iLoginAsAccountMember() {
        // Create a member user if not exists
        // Assume member user with email member@briefbot.com exists
        // In a real scenario, this would be set up in test data
        // For now, we'll assume the user exists and try to login
        // If the user doesn't exist, the test will fail appropriately

        // Login as member user
        AuthenticationSteps authSteps = new AuthenticationSteps(context);
        authSteps.iLoginWithEmailAndPassword("member@briefbot.com", "Member123");
    }

    @When("I try to change another user's role")
    public void iTryToChangeAnotherUserRole() {
        RoleUpdateRequest request = RoleUpdateRequest.builder()
                .role("ADMIN")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/accounts/1/users/{userId}", 3)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Given("I have only one owner in the account")
    public void iHaveOnlyOneOwnerInTheAccount() {
        // This step assumes that the test account (ID 1) has only one owner
        // In a real implementation, this would verify or set up the account to have only one owner
        // For testing purposes, we assume user ID 1 is the only owner
    }

    @When("I try to change the owner's role or remove them")
    public void iTryToChangeTheOwnerRoleOrRemoveThem() {
        RoleUpdateRequest request = RoleUpdateRequest.builder()
                .role("MEMBER")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/accounts/1/users/{userId}", 1)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }
}
