@authentication
Feature: No Account Assigned
  As a new user
  I'm not assigned to any account so I can't create any content

  Background:
    Given the API is available
    And I generate a random email

  @login @smoke
  Scenario: Login and create service if user is not assigned to an account
    Given I register a user with the random email, given name "Login", and family name "Test"
    When I login with the random email and password "Secure123"
    Then the response status should be 200
    And the response should contain login confirmation
    And I should be authenticated
    And I create a service with name "Initial Service" and price 1000.00
    Then the response status should be 403
    And the response should contain error message "No Account Assigned"