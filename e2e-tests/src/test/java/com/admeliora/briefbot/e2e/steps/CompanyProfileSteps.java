package com.admeliora.briefbot.e2e.steps;

import com.admeliora.briefbot.e2e.config.TestConfig;
import com.admeliora.briefbot.e2e.model.CompanyProfileRequest;
import com.admeliora.briefbot.e2e.model.CompanyProfileResponse;
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
 * Step definitions for Company Profile operations
 */
public class CompanyProfileSteps {

    private final TestContext context;

    @Autowired
    public CompanyProfileSteps(TestContext context) {
        this.context = context;
    }

    @When("I create a company profile with name {string}")
    public void iCreateACompanyProfileWithName(String name) {
        CompanyProfileRequest request = CompanyProfileRequest.builder()
                .name(name)
                .description("Test company description")
                .website("https://testcompany.com")
                .address("Test Address 123")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/company-profiles")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the company profile should be created")
    public void theCompanyProfileShouldBeCreated() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(201);

        CompanyProfileResponse profile = response.as(CompanyProfileResponse.class);
        assertThat(profile.getId()).isNotNull();

        context.put("companyProfileId", profile.getId());
    }

    @Given("I have an existing company profile")
    public void iHaveAnExistingCompanyProfile() {
        if (context.get("companyProfileId") == null) {
            iCreateACompanyProfileWithName("Test Company");
            assertThat(context.getLastResponse().getStatusCode()).isEqualTo(201);
        }
    }

    @When("I get the company profile by id")
    public void iGetTheCompanyProfileById() {
        Long profileId = (Long) context.get("companyProfileId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/company-profiles/{profileId}", profileId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the response should contain the company profile details")
    public void theResponseShouldContainTheCompanyProfileDetails() {
        Response response = context.getLastResponse();
        CompanyProfileResponse profile = response.as(CompanyProfileResponse.class);
        assertThat(profile.getId()).isNotNull();
        assertThat(profile.getName()).isNotNull();
    }

    @When("I update the company description and contact info")
    public void iUpdateTheCompanyDescriptionAndContactInfo() {
        Long profileId = (Long) context.get("companyProfileId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body("{\"description\": \"Updated description\", \"website\": \"https://updated.com\"}")
                .when()
                .put("/company-profiles/{profileId}", profileId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the company profile should be updated")
    public void theCompanyProfileShouldBeUpdated() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @When("I list all company profiles for account 1")
    public void iListAllCompanyProfilesForAccount() {
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/company-profiles?accountId=1")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the response should contain a list of company profiles")
    public void theResponseShouldContainAListOfCompanyProfiles() {
        Response response = context.getLastResponse();
        List<CompanyProfileResponse> profiles = response.jsonPath().getList("", CompanyProfileResponse.class);
        assertThat(profiles).isNotNull();
    }

    @When("I create a company profile with empty name")
    public void iCreateACompanyProfileWithEmptyName() {
        CompanyProfileRequest request = CompanyProfileRequest.builder()
                .name("")
                .description("Test description")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/company-profiles")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Given("I already have a company profile for account 1")
    public void iAlreadyHaveACompanyProfileForAccount() {
        iCreateACompanyProfileWithName("Existing Company");
        assertThat(context.getLastResponse().getStatusCode()).isEqualTo(201);
    }

    @When("I try to create another company profile for the same account")
    public void iTryToCreateAnotherCompanyProfileForTheSameAccount() {
        CompanyProfileRequest request = CompanyProfileRequest.builder()
                .name("Another Company")
                .description("Another description")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/company-profiles")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }
}
