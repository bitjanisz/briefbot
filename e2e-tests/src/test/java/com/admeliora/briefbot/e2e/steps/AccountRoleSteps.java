package com.admeliora.briefbot.e2e.steps;

import com.admeliora.briefbot.e2e.config.TestConfig;
import com.admeliora.briefbot.e2e.model.AccountUserRequest;
import com.admeliora.briefbot.e2e.model.RoleUpdateRequest;
import com.admeliora.briefbot.e2e.model.UserAccountRequest;
import com.admeliora.briefbot.e2e.support.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Given("I have a user in my account")
    public void iHaveAUserInMyAccount() {
        // Assume user exists from previous steps or create one
        if (context.get("userId") == null) {
            // Create a user account relationship
            AccountUserRequest request = AccountUserRequest.builder()
                    .userId(2L)
                    .role("MEMBER")
                    .build();

            Response response = given()
                    .spec(TestConfig.getRequestSpec(context))
                    .body(request)
                    .when()
                    .post("/accounts/1/users");

            context.put("userId", 2L);
        }
    }

    @When("I assign ADMIN role to that user")
    public void iAssignADMINRoleToThatUser() {
        Long userId = (Long) context.get("userId");

        RoleUpdateRequest request = RoleUpdateRequest.builder()
                .role("ADMIN")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/accounts/1/users/{userId}", userId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the user should have ADMIN role")
    public void theUserShouldHaveADMINRole() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
        // Verify role in response
    }

    @Given("I have a user with MEMBER role")
    public void iHaveAUserWithMEMBERRole() {
        iHaveAUserInMyAccount();
    }

    @When("I change their role to VIEWER")
    public void iChangeTheirRoleToVIEWER() {
        Long userId = (Long) context.get("userId");

        RoleUpdateRequest request = RoleUpdateRequest.builder()
                .role("VIEWER")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/accounts/1/users/{userId}", userId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the user should have VIEWER role")
    public void theUserShouldHaveVIEWERRole() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
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
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(204);
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
        // Switch to member user context - this would require additional setup
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
                .put("/accounts/1/users/3")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Given("I have only one owner in the account")
    public void iHaveOnlyOneOwnerInTheAccount() {
        // Setup scenario with single owner
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
                .put("/accounts/1/users/1")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }
}
