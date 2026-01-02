package com.admeliora.briefbot.e2e.steps;

import com.admeliora.briefbot.e2e.config.TestConfig;
import com.admeliora.briefbot.e2e.model.CreateUserRequest;
import com.admeliora.briefbot.e2e.model.UpdateUserRequest;
import com.admeliora.briefbot.e2e.model.UserResponse;
import com.admeliora.briefbot.e2e.support.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for User management operations
 */
public class UserSteps {

    private final TestContext context;

    @Autowired
    public UserSteps(TestContext context) {
        this.context = context;
    }

    @When("I create a user with email {string}, given name {string}, family name {string}")
    public void iCreateAUserWithEmailGivenNameFamilyName(String email, String givenName, String familyName) {
        CreateUserRequest request = CreateUserRequest.builder()
                .email(email)
                .givenName(givenName)
                .familyName(familyName)
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/users")
                .then()
                .extract().response();

        context.setLastResponse(response);

        if (response.getStatusCode() == 201) {
            UserResponse userResponse = response.as(UserResponse.class);
            if (context.get("firstUserId") == null) {
                context.put("firstUserId", userResponse.id());
            } else if (context.get("secondUserId") == null) {
                context.put("secondUserId", userResponse.id());
            }
            context.put("createdUserId", userResponse.id());
        }
    }

    @Then("the user should be created with email {string}")
    public void theUserShouldBeCreatedWithEmail(String email) {
        Response response = context.getLastResponse();
        UserResponse user = response.as(UserResponse.class);
        assertThat(user.email()).isEqualTo(email);
    }

    @When("I get the created user by id")
    public void iGetTheCreatedUserById() {
        Long userId = (Long) context.get("createdUserId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/users/{userId}", userId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the response should contain user with email {string}")
    public void theResponseShouldContainUserWithEmail(String email) {
        Response response = context.getLastResponse();
        UserResponse user = response.as(UserResponse.class);
        assertThat(user.email()).isEqualTo(email);
    }

    @When("I update the user with given name {string}, family name {string}")
    public void iUpdateTheUserWithGivenNameFamilyName(String givenName, String familyName) {
        Long userId = (Long) context.get("createdUserId");

        UpdateUserRequest request = UpdateUserRequest.builder()
                .givenName(givenName)
                .familyName(familyName)
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/users/{userId}", userId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the response should contain user with name {string}")
    public void theResponseShouldContainUserWithName(String expectedName) {
        Response response = context.getLastResponse();
        UserResponse user = response.as(UserResponse.class);
        String fullName = user.givenName() + " " + user.familyName();
        assertThat(fullName).isEqualTo(expectedName);
    }

    @When("I delete the created user")
    public void iDeleteTheCreatedUser() {
        Long userId = (Long) context.get("createdUserId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .delete("/users/{userId}", userId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @When("I update the second user with email {string}")
    public void iUpdateTheSecondUserWithEmail(String email) {
        // Assume we have a second user ID stored
        Long userId = (Long) context.get("secondUserId");

        UpdateUserRequest request = UpdateUserRequest.builder()
                .email(email)
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/users/{userId}", userId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }
}
