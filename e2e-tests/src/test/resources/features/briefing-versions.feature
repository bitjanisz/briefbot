@briefing-versions @smoke
Feature: Briefing Version Management
  As a user
  I want to manage briefing versions
  So that I can track changes to client requirements

  Background:
    Given the API is available
    And I login with default user
    And I create a sample client

  @create
  Scenario: Create new briefing version
    Given I have an existing briefing
    When I create a new briefing version
    Then the response status should be 201
    And the briefing should have a new version with incremented number

  @read
  Scenario: Get briefing version by ID
    Given I have a briefing with multiple versions
    When I get a specific briefing version by id
    Then the response status should be 200
    And the response should contain the briefing version details

  @update
  Scenario: Update briefing version responses
    Given I have an existing briefing version
    When I update the client responses
    Then the response status should be 200
    And the briefing version should be updated

  @list
  Scenario: List versions for a briefing
    Given I have a briefing with multiple versions
    When I list all versions for that briefing
    Then the response status should be 200
    And the response should contain all briefing versions ordered by version number

  @validation
  Scenario: Cannot update completed version
    Given I have a briefing version that is finalized
    When I try to update the responses
    Then the response status should be 400

  @edge-case
  Scenario: Version numbers are sequential
    Given I have a briefing with version 1
    When I create a new briefing version
    Then the new briefing version should have number 2
    When I create another briefing version
    Then the new briefing version should have number 3
