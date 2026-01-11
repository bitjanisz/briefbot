@account-roles @smoke
Feature: Account Role Management
  As an account owner
  I want to manage user roles in my account
  So that I can control access permissions

  Background:
    Given the API is available
    And I login as account admin

  @assign-role
  Scenario: Assign role to user
    Given I register a new user
    When I assign "ADMIN" role to that user
    Then the response status should be 200
    And the user should have "ADMIN" role

  @change-role
  Scenario: Change user role
    Given I register a new user
    When I change their role to "VIEWER"
    Then the response status should be 200
    And the user should have "VIEWER" role

  @remove-user
  Scenario: Remove user from account
    Given I register a new user
    When I assign "ADMIN" role to that user
    When I remove that user from the account role
    Then the response status should be 204
    And the user should no longer be in the account

  @list-users
  Scenario: List users in account with roles
    Given I register a new user
    When I assign "ADMIN" role to that user
    When I list all users in my account
    Then the response status should be 200
    And the response should contain users with their roles

  @validation
  Scenario: Only owner can change roles
    Given I register a new user
    When I assign "ADMIN" role to that user
    Given I login as account member
    When I try to change another user's role
    Then the response status should be 403

  @edge-case
  Scenario: Cannot remove last owner
    Given I register a new user
    When I assign "ADMIN" role to that user
    When I try to change the owner's role or remove them
    Then the response status should be 400
    And the response should contain error message "cannot remove last owner"
