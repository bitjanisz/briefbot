@briefings @smoke
Feature: Briefing Management
  As a user
  I want to manage briefings
  So that I can collect client requirements

  Background:
    Given the API is available
    And I login with default user

  @create
  Scenario: Create a new briefing
    Given I have an existing client
    When I create a briefing for that client with title "Website Requirements"
    Then the response status should be 201
    And the briefing should be created

  @create-version
  Scenario: Create briefing version
    Given I have an existing briefing
    When I create a new version for that briefing
    Then the response status should be 201
    And the briefing should have 2 versions

  @read
  Scenario: Get briefing by ID
    Given I have an existing briefing
    When I get the briefing by id
    Then the response status should be 200
    And the response should contain the briefing details

  @update
  Scenario: Update briefing content
    Given I have an existing briefing
    When I update the briefing requirements
    Then the response status should be 200
    And the briefing should be updated

  @delete @skipped
  Scenario: Delete a briefing
    Given I have an existing briefing
    When I delete the briefing
    Then the response status should be 204

  @list
  Scenario: List all briefings for account
    When I list all briefings for the default account
    Then the response status should be 200
    And the response should contain a list of briefings

  @validation
  Scenario: Cannot create briefing without client
    When I create a briefing without specifying a client
    Then the response status should be 400

  @edge-case
  Scenario: Cannot modify finalized briefing
    Given I have a briefing marked as finalized
    When I try to update the briefing
    Then the response status should be 400
    And the response should contain error message "cannot modify finalized briefing"
