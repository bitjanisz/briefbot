@clients @smoke
Feature: Client Management
  As a user
  I want to manage clients
  So that I can track my customers

  Background:
    Given the API is available
    And I login with default user

  @create
  Scenario: Create a new client
    When I create a client with name "ACME Corp" and email "contact@acme.com"
    Then the response status should be 201
    And the response should contain the client with name "ACME Corp"

  @read
  Scenario: Get client by ID
    When I create a client with name "Test Client" and email "test@client.com"
    Then the response status should be 201
    When I get the client by id
    Then the response status should be 200
    And the response should contain the client with name "Test Client"

  @update @skipped
  Scenario: Update an existing client
    When I create a client with name "Original Name" and email "original@test.com"
    Then the response status should be 201
    When I update the client name to "Updated Name"
    Then the response status should be 200
    And the response should contain the client with name "Updated Name"

  @delete
  Scenario: Delete a client
    When I create a client with name "To Delete" and email "delete@test.com"
    Then the response status should be 201
    When I delete the client
    Then the response status should be 204

  @list
  Scenario: List all clients for an account
    When I list all clients for the default account
    Then the response status should be 200
    And the response should contain a list of clients
