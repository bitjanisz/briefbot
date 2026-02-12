package com.admeliora.briefbot.e2e.steps;

import com.admeliora.briefbot.e2e.config.TestConfig;
import com.admeliora.briefbot.e2e.model.response.AuthResponse;
import com.admeliora.briefbot.e2e.model.request.LoginRequest;
import com.admeliora.briefbot.e2e.model.request.RegisterUserRequest;
import com.admeliora.briefbot.e2e.support.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Authentication operations (registration and login)
 */
@Slf4j
public class AuthenticationSteps {

    private static final String JWT_COOKIE_NAME = "BRIEFBOT_JWT";

    private final TestContext context;

    @Autowired
    public AuthenticationSteps(TestContext context) {
        this.context = context;
    }

    // Registration Steps

    @Given("I generate a random email")
    public void iGenerateARandomEmail() {
        String randomEmail = context.generateRandomEmail();
        log.info("Generated random email: {}", randomEmail);
    }

    @When("I register a user with the random email, given name {string}, and family name {string}")
    public void iRegisterAUserWithTheRandomEmailGivenNameAndFamilyName(String givenName, String familyName) {
        String email = context.getRandomEmail();
        assertThat(email).as("Random email should be generated first").isNotNull();
        iRegisterAUserWithEmailGivenNameAndFamilyName(email, givenName, familyName);
    }

    @When("I register a user with email {string}, given name {string}, and family name {string}")
    public void iRegisterAUserWithEmailGivenNameAndFamilyName(String email, String givenName, String familyName) {
        RegisterUserRequest request = RegisterUserRequest.builder()
                .email(email)
                .givenName(givenName)
                .familyName(familyName)
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/auth/register")
                .then()
                .extract()
                .response();

        context.setLastResponse(response);

        // Store email for later use
        context.put("registered.email", email);

        // For test profile, the password is always "Secure123" (hardcoded in TestPasswordGenerator)
        if (response.getStatusCode() == 201) {
            context.put("temporary.password", "Secure123");
        } else {
            throw new IllegalStateException("User creation failed with status: " + response.getStatusCode());
        }
    }

    @When("I register a user without email")
    public void iRegisterAUserWithoutEmail() {
        RegisterUserRequest request = RegisterUserRequest.builder()
                .givenName("Test")
                .familyName("User")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/auth/register")
                .then()
                .extract()
                .response();

        context.setLastResponse(response);
    }

    @Given("a user exists with email {string}")
    public void aUserExistsWithEmail(String email) {
        // First register the user
        RegisterUserRequest request = RegisterUserRequest.builder()
                .email(email)
                .givenName("Existing")
                .familyName("User")
                .build();

        given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/auth/register");
    }

    @And("the response should contain registration confirmation")
    public void theResponseShouldContainRegistrationConfirmation() {
        Response response = context.getLastResponse();
        AuthResponse authResponse = response.as(AuthResponse.class);

        assertThat(authResponse.id())
                .as("User ID should be present")
                .isNotNull();
        assertThat(authResponse.email())
                .as("Email should be present")
                .isNotNull();
        assertThat(authResponse.message())
                .as("Message should contain password info")
                .contains("password");
    }

    @And("the user should receive a temporary password email")
    public void theUserShouldReceiveATemporaryPasswordEmail() {
        // This is a placeholder - in real scenario we would verify email was sent
        // For now we just verify the response indicates success
        Response response = context.getLastResponse();
        AuthResponse authResponse = response.as(AuthResponse.class);

        assertThat(authResponse.message())
                .as("Message should indicate email was sent")
                .containsIgnoringCase("email");
    }

    // Login Steps

    @Given("a user is registered with email {string} and password {string}")
    public void aUserIsRegisteredWithEmailAndPassword(String email, String password) {
        // Register user first
        RegisterUserRequest registerRequest = RegisterUserRequest.builder()
                .email(email)
                .givenName("Test")
                .familyName("User")
                .build();

        given()
                .spec(TestConfig.getRequestSpec(context))
                .body(registerRequest)
                .when()
                .post("/auth/register");

        // Store the credentials for later use
        context.put("test.email", email);
        context.put("test.password", password);
    }

    @Given("I login with default user")
    public void iLoginWithDefaultUser() {
        iLoginWithEmailAndPassword("admin@briefbot.com", "Admin123");
    }

    @Given("I login as account admin")
    public void iLoginAsAccountAdmin() {
        iLoginWithEmailAndPassword("admin@briefbot.com", "Admin123");
    }

    @When("I login with email {string} and password {string}")
    public void iLoginWithEmailAndPassword(String email, String password) {
        LoginRequest request = LoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .response();

        context.setLastResponse(response);

        // Save JWT cookies for subsequent authenticated requests
        if (response.getStatusCode() == 200) {
            context.setSessionCookies(response.getDetailedCookies());
            log.info("JWT cookies saved for user: {}", email);
        }
    }

    @When("I login with the random email and password {string}")
    public void iLoginWithTheRandomEmailAndPassword(String password) {
        String email = context.getRandomEmail();
        assertThat(email).as("Random email should be generated first").isNotNull();
        iLoginWithEmailAndPassword(email, password);
    }

    @When("I try to login with email {string} and password {string}")
    public void iTryToLoginWithEmailAndPassword(String email, String password) {
        iLoginWithEmailAndPassword(email, password);
    }

    @When("I login without email or password")
    public void iLoginWithoutEmailOrPassword() {
        LoginRequest request = LoginRequest.builder().build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .response();

        context.setLastResponse(response);
    }

    @And("the response should contain login confirmation")
    public void theResponseShouldContainLoginConfirmation() {
        Response response = context.getLastResponse();
        AuthResponse authResponse = response.as(AuthResponse.class);

        assertThat(authResponse.id())
                .as("User ID should be present")
                .isNotNull();
        assertThat(authResponse.email())
                .as("Email should be present")
                .isNotNull();
        assertThat(authResponse.message())
                .as("Message should indicate successful login")
                .containsIgnoringCase("success");
    }

    @And("I should be authenticated")
    public void iShouldBeAuthenticated() {
        // Verify JWT cookie was set
        Response response = context.getLastResponse();
        assertThat(response.getCookies())
                .as("JWT cookie should be present")
                .isNotEmpty();

        // Verify BRIEFBOT_JWT cookie specifically
        assertThat(response.getCookie(JWT_COOKIE_NAME))
                .as(JWT_COOKIE_NAME + " cookie should be present")
                .isNotNull();
    }

    @Given("a user registered via OAuth with email {string}")
    public void aUserRegisteredViaOAuthWithEmail(String email) {
        // This would typically be set up through a test fixture
        // For now, we'll just note that this user should exist with oidc_sub but no password
        context.put("oauth.user.email", email);
    }

    // Flow Steps

    @And("I save the temporary password")
    public void iSaveTheTemporaryPassword() {
        // In real scenario, this would extract password from email
        // For testing, we use the mock password stored during registration
        String password = context.get("temporary.password", String.class);
        context.put("saved.password", password);
    }

    @When("I login with the saved email and password")
    public void iLoginWithTheSavedEmailAndPassword() {
        String email = context.get("registered.email", String.class);
        String password = context.get("saved.password", String.class);

        assertThat(email).as("Email should be saved").isNotNull();
        assertThat(password).as("Password should be saved").isNotNull();

        iLoginWithEmailAndPassword(email, password);
    }

    @When("I access a protected resource")
    public void iAccessAProtectedResource() {
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/accounts")
                .then()
                .extract()
                .response();

        context.setLastResponse(response);
    }
}

