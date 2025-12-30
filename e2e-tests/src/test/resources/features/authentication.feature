@authentication
Feature: User Authentication
  As a new user
  I want to register and login with email and password
  So that I can access the application

  Background:
    Given the API is available
    And I generate a random email

  @register @smoke
  Scenario: Register a new user successfully
    When I register a user with the random email, given name "Test", and family name "User"
    Then the response status should be 201
    And the response should contain registration confirmation
    And the user should receive a temporary password email

  @register @negative
  Scenario: Cannot register with existing email
    Given I register a user with the random email, given name "Existing", and family name "User"
    When I register a user with the random email, given name "Existing", and family name "User"
    Then the response status should be 400
    And the response should contain error message "already exists"

  @register @validation
  Scenario: Cannot register with invalid email
    When I register a user with email "invalid-email", given name "Test", and family name "User"
    Then the response status should be 400

  @register @validation
  Scenario: Cannot register without required fields
    When I register a user without email
    Then the response status should be 400

  @login @smoke
  Scenario: Login with valid credentials
    Given I register a user with the random email, given name "Login", and family name "Test"
    When I login with the random email and password "Secure123"
    Then the response status should be 200
    And the response should contain login confirmation
    And I should be authenticated

  @login @negative
  Scenario: Cannot login with invalid password
    Given I register a user with the random email, given name "Test", and family name "User"
    When I login with the random email and password "WrongPassword"
    Then the response status should be 400
    And the response should contain error message "Invalid email or password"

  @login @negative
  Scenario: Cannot login with non-existent email
    When I login with email "nonexistent@example.com" and password "AnyPassword"
    Then the response status should be 400
    And the response should contain error message "Invalid email or password"

  @login @validation
  Scenario: Cannot login without credentials
    When I login without email or password
    Then the response status should be 400

#  @oauth @info
#  Scenario: OAuth user cannot login with password
#    Given a user registered via OAuth with email "oauth.user@example.com"
#    When I try to login with email "oauth.user@example.com" and password "AnyPassword"
#    Then the response status should be 400
#    And the response should contain error message "registered via OAuth"

  @integration
  Scenario: Complete registration and login flow
    Given I register a user with the random email, given name "Flow", and family name "Test"
    Then the response status should be 201
    And I save the temporary password
    When I login with the saved email and password
    Then the response status should be 200
    And I should be authenticated
    When I access a protected resource
    Then the response status should be 200