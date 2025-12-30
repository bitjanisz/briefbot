@accounts @smoke
Feature: Account Management
  As a user
  I want to manage accounts
  So that I can organize my work

  Background:
    Given the API is available
    And I login with default user

  @create
  Scenario: Create a new account
    When I create an account with name "My Test Account"
    Then the response status should be 201
    And the response should contain account details
    When I get the created account
    Then the response status should be 200
    And the response should contain account with data:
      | name  | My Test Account |

  @list
  Scenario: List all accounts
    When I list all accounts
    Then the response status should be 200
    And the response should contain a list of accounts

