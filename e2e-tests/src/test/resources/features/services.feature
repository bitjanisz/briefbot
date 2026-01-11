@services @smoke
Feature: Service Management
  As a user
  I want to manage services
  So that I can define what I offer to clients

  Background:
    Given the API is available
    And I login with default user

  @create
  Scenario: Create a new service
    When I create a service with name "Web Development" and price 5000.00
    Then the response status should be 201
    And the response should contain the service with name "Web Development"

#  @create-with-relations @skipped
#  Scenario: Create a service with related services
#    When I create a service with name "Backend Development" and price 3000.00
#    And I create a service with name "Full Stack Development" that depends on the previous service
#    Then the response status should be 201
#    And the service should have 1 related service

  @read
  Scenario: Get service by ID
    When I create a service with name "Consulting" and price 2000.00
    Then the response status should be 201
    When I get the service by id
    Then the response status should be 200
    And the response should contain the service with name "Consulting"

  @update @skipped
  Scenario: Update service price
    When I create a service with name "Design" and price 1500.00
    Then the response status should be 201
    When I update the service price to 2500.00
    Then the response status should be 200

  @delete
  Scenario: Delete a service
    When I create a service with name "Temporary Service" and price 1000.00
    Then the response status should be 201
    When I delete the service
    Then the response status should be 204

  @list
  Scenario: List all services for an account
    When I list all services for account 1
    Then the response status should be 200
    And the response should contain a list of services

