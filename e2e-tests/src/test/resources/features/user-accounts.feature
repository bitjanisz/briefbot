@user-accounts @smoke
Feature: User Account Membership Management
  As an account admin
  I want to manage user memberships in accounts
  So that I can control who has access to account resources

  Background:
    Given the API is available
    And I login as account admin

  @add-user
  Scenario: Add user to account
    Given I have a registered user not in any account
    When I add that user to my account with MEMBER role
    Then the response status should be 200
    And the user should be added to the account

  @change-membership
  Scenario: Change user role in account
    Given I have a user in my account with MEMBER role
    When I change their role to ADMIN
    Then the response status should be 200
    And the user should have ADMIN role in the account

  @remove-user
  Scenario: Remove user from account
    Given I have a user in my account
    When I remove that user from the account
    Then the response status should be 200
    And the user should no longer have access to account resources

  @list-members
  Scenario: List account members
    When I list all members of my account
    Then the response status should be 200
    And the response should contain all account members with their roles

  @validation
  Scenario: Cannot add user already in account
    Given I have a user already in my account
    When I try to add them again
    Then the response status should be 400
    And the response should contain error message "user already in account"

  @edge-case
  Scenario: User can be in multiple accounts
    Given I have a user in one account
    When I add them to another account
    Then the response status should be 200
    And the user should be in both accounts
