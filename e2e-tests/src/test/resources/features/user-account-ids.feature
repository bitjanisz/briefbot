@user-account-ids @smoke
Feature: User Account ID Validation
  As a system
  I want to validate user account relationships
  So that I can ensure data integrity

  Background:
    Given the API is available
    And I login with default user

  @validation
  Scenario: User account ID must be unique
    Given I have a user account relationship
    When I try to create another relationship with same user and account
    Then the response status should be 400
    And the response should contain error message "duplicate user account relationship"

  @edge-case
  Scenario: User can belong to multiple accounts
    Given I have a user in account A
    When I add the same user to account B
    Then the response status should be 200
    And the user should be in both accounts

  @edge-case
  Scenario: Account can have multiple users
    Given I have account with user X
    When I add user Y to the same account
    Then the response status should be 200
    And both users should be in the account
