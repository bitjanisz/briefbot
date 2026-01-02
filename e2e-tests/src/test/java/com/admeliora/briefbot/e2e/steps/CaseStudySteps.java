package com.admeliora.briefbot.e2e.steps;

import com.admeliora.briefbot.e2e.config.TestConfig;
import com.admeliora.briefbot.e2e.model.CaseStudyRequest;
import com.admeliora.briefbot.e2e.model.CaseStudyResponse;
import com.admeliora.briefbot.e2e.model.CaseStudyUpdateRequest;
import com.admeliora.briefbot.e2e.model.PublishRequest;
import com.admeliora.briefbot.e2e.model.StatusUpdateRequest;
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
 * Step definitions for Case Study operations
 */
public class CaseStudySteps {

    private final TestContext context;

    @Autowired
    public CaseStudySteps(TestContext context) {
        this.context = context;
    }

    @When("I create a case study with title {string}")
    public void iCreateACaseStudyWithTitle(String title) {
        CaseStudyRequest request = CaseStudyRequest.builder()
                .title(title)
                .description("Test case study description")
                .results("Successful project completion")
                .technologies("Java, Spring Boot")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/case-studies")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the case study should be created")
    public void theCaseStudyShouldBeCreated() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(201);

        CaseStudyResponse caseStudy = response.as(CaseStudyResponse.class);
        assertThat(caseStudy.getId()).isNotNull();

        context.put("caseStudyId", caseStudy.getId());
    }

    @Given("I have an existing case study")
    public void iHaveAnExistingCaseStudy() {
        if (context.get("caseStudyId") == null) {
            iCreateACaseStudyWithTitle("Test Case Study");
            assertThat(context.getLastResponse().getStatusCode()).isEqualTo(201);
        }
    }

    @When("I get the case study by id")
    public void iGetTheCaseStudyById() {
        Long caseStudyId = (Long) context.get("caseStudyId");

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/case-studies/{caseStudyId}", caseStudyId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the response should contain the case study details")
    public void theResponseShouldContainTheCaseStudyDetails() {
        Response response = context.getLastResponse();
        CaseStudyResponse caseStudy = response.as(CaseStudyResponse.class);
        assertThat(caseStudy.getId()).isNotNull();
        assertThat(caseStudy.getTitle()).isNotNull();
    }

    @When("I update the case study description and results")
    public void iUpdateTheCaseStudyDescriptionAndResults() {
        Long caseStudyId = (Long) context.get("caseStudyId");

        CaseStudyUpdateRequest request = CaseStudyUpdateRequest.builder()
                .description("Updated description")
                .results("Updated results")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/case-studies/{caseStudyId}", caseStudyId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the case study should be updated")
    public void theCaseStudyShouldBeUpdated() {
        Response response = context.getLastResponse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @When("I list all case studies for account 1")
    public void iListAllCaseStudiesForAccount() {
        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .when()
                .get("/case-studies?accountId=1")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the response should contain a list of case studies")
    public void theResponseShouldContainAListOfCaseStudies() {
        Response response = context.getLastResponse();
        List<CaseStudyResponse> caseStudies = response.jsonPath().getList("", CaseStudyResponse.class);
        assertThat(caseStudies).isNotNull();
    }

    @Given("I have a draft case study")
    public void iHaveADraftCaseStudy() {
        iHaveAnExistingCaseStudy();
        // Assume it's draft by default
    }

    @When("I publish the case study")
    public void iPublishTheCaseStudy() {
        Long caseStudyId = (Long) context.get("caseStudyId");

        PublishRequest request = PublishRequest.builder()
                .published(true)
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/case-studies/{caseStudyId}/publish", caseStudyId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Then("the case study should be marked as published")
    public void theCaseStudyShouldBeMarkedAsPublished() {
        Response response = context.getLastResponse();
        CaseStudyResponse caseStudy = response.as(CaseStudyResponse.class);
        assertThat(caseStudy.getPublished()).isTrue();
    }

    @When("I create a case study with empty title")
    public void iCreateACaseStudyWithEmptyTitle() {
        CaseStudyRequest request = CaseStudyRequest.builder()
                .title("")
                .description("Test description")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/case-studies")
                .then()
                .extract().response();

        context.setLastResponse(response);
    }

    @Given("I have a case study without results")
    public void iHaveACaseStudyWithoutResults() {
        CaseStudyRequest request = CaseStudyRequest.builder()
                .title("Incomplete Case Study")
                .description("Test description")
                .technologies("Java")
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .post("/case-studies");

        Long caseStudyId = response.jsonPath().getLong("id");
        context.put("caseStudyId", caseStudyId);
    }

    @When("I try to publish it")
    public void iTryToPublishIt() {
        Long caseStudyId = (Long) context.get("caseStudyId");

        PublishRequest request = PublishRequest.builder()
                .published(true)
                .build();

        Response response = given()
                .spec(TestConfig.getRequestSpec(context))
                .body(request)
                .when()
                .put("/case-studies/{caseStudyId}/publish", caseStudyId)
                .then()
                .extract().response();

        context.setLastResponse(response);
    }
}
