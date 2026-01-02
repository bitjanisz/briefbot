@company-profiles @smoke
Feature: Company Profile Management
  As a user
  I want to manage company profiles
  So that I can present my business information

  Background:
    Given the API is available
    And I login with default user

  @create
  Scenario: Create a new company profile
    When I create a company profile with name "My Company Inc"
    Then the response status should be 201
    And the company profile should be created

  @read
  Scenario: Get company profile by ID
    Given I have an existing company profile
    When I get the company profile by id
    Then the response status should be 200
    And the response should contain the company profile details

  @update
  Scenario: Update company profile
    Given I have an existing company profile
    When I update the company description and contact info
    Then the response status should be 200
    And the company profile should be updated

  @delete @skipped
  Scenario: Delete a company profile
    Given I have an existing company profile
    When I delete the company profile
    Then the response status should be 204

  @list
  Scenario: List company profiles for account
    When I list all company profiles for account 1
    Then the response status should be 200
    And the response should contain a list of company profiles

  @validation
  Scenario: Cannot create company profile with empty name
    When I create a company profile with empty name
    Then the response status should be 400

  @edge-case
  Scenario: Only one company profile per account
    Given I already have a company profile for account 1
    When I try to create another company profile for the same account
    Then the response status should be 400
    And the response should contain error message "only one profile per account"
