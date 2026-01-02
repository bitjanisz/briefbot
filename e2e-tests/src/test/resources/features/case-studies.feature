@case-studies @smoke
Feature: Case Study Management
  As a user
  I want to manage case studies
  So that I can showcase my work

  Background:
    Given the API is available
    And I login with default user

  @create
  Scenario: Create a new case study
    When I create a case study with title "E-commerce Platform Success"
    Then the response status should be 201
    And the case study should be created

  @read
  Scenario: Get case study by ID
    Given I have an existing case study
    When I get the case study by id
    Then the response status should be 200
    And the response should contain the case study details

  @update
  Scenario: Update case study content
    Given I have an existing case study
    When I update the case study description and results
    Then the response status should be 200
    And the case study should be updated

  @delete @skipped
  Scenario: Delete a case study
    Given I have an existing case study
    When I delete the case study
    Then the response status should be 204

  @list
  Scenario: List case studies for account
    When I list all case studies for account 1
    Then the response status should be 200
    And the response should contain a list of case studies

  @publish
  Scenario: Publish case study
    Given I have a draft case study
    When I publish the case study
    Then the response status should be 200
    And the case study should be marked as published

  @validation
  Scenario: Cannot create case study with empty title
    When I create a case study with empty title
    Then the response status should be 400

  @edge-case
  Scenario: Cannot publish incomplete case study
    Given I have a case study without results
    When I try to publish it
    Then the response status should be 400
    And the response should contain error message "incomplete case study"
