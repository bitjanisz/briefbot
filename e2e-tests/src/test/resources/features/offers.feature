@offers @smoke
Feature: Offer Management
  As a user
  I want to manage offers
  So that I can create proposals for clients

  Background:
    Given the API is available
    And I login with default user

  @create
  Scenario: Create a new offer
    Given I have an existing client
    When I create an offer for that client with title "Website Redesign"
    Then the response status should be 201
    And the offer should be created with status "DRAFT"

  @create-version
  Scenario: Create offer version
    Given I have an existing offer
    When I create a new version for that offer
    Then the response status should be 201
    And the offer should have 2 versions

  @read
  Scenario: Get offer by ID
    Given I have an existing offer
    When I get the offer by id
    Then the response status should be 200
    And the response should contain the offer details

  @update
  Scenario: Update offer status
    Given I have an existing offer in DRAFT status
    When I update the offer status to "SENT"
    Then the response status should be 200
    And the offer status should be "SENT"

  @delete @skipped
  Scenario: Delete an offer
    Given I have an existing offer
    When I delete the offer
    Then the response status should be 204

  @list
  Scenario: List all offers for account
    When I list all offers for the default account
    Then the response status should be 200
    And the response should contain a list of offers

  @validation
  Scenario: Cannot create offer without client
    When I create an offer without specifying a client
    Then the response status should be 400

  @edge-case
  Scenario: Cannot update offer to invalid status transition
    Given I have an offer in status "ACCEPTED"
    When I try to update status back to "DRAFT"
    Then the response status should be 400
    And the response should contain error message "invalid status transition"
