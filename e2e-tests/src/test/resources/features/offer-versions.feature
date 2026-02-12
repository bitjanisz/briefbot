@offer-versions @smoke
Feature: Offer Version Management
  As a user
  I want to manage offer versions
  So that I can track changes and improvements to offers

  Background:
    Given the API is available
    And I login with default user

  @create
  Scenario: Create new offer version
    Given I have an existing offer
    When I create a new version for that offer
    Then the response status should be 201
    And the offer should have a new version with incremented number

  @read
  Scenario: Get offer version by ID
    Given I have an offer with multiple versions
    When I get a specific offer version by id
    Then the response status should be 200
    And the response should contain the offer version details

  @update
  Scenario: Update offer version content
    Given I have an existing offer version
    When I update the version content and pricing
    Then the response status should be 200
    And the offer version should be updated

  @list
  Scenario: List versions for an offer
    Given I have an offer with multiple versions
    When I list all versions for that offer
    Then the response status should be 200
    And the response should contain all offer versions ordered by version number

  @validation
  Scenario: Cannot update finalized version
    Given I have an offer version that is finalized
    When I try to update the content
    Then the response status should be 400

  @edge-case
  Scenario: Version numbers are sequential
    Given I have an offer with version 1
    When I create a new offer version
    Then the new offer version should have number 2
    When I create another offer version
    Then the new offer version should have number 3
