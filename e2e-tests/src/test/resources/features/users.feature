@users @smoke
Feature: User Management
  As an admin
  I want to manage users
  So that I can create, update, and delete user accounts

  Background:
    Given the API is available
    And I login as account admin

  @create-user
  Scenario: Create a new user
    When I create a user with email "newuser@example.com", given name "New", family name "User"
    Then the response status should be 201
    And the response should contain user details
    And the user should be created with email "newuser@example.com"

  @get-user
  Scenario: Get user by ID
    When I create a user with email "getuser@example.com", given name "Get", family name "User"
    Then the response status should be 201
    When I get the created user by id
    Then the response status should be 200
    And the response should contain user with email "getuser@example.com"

  @update-user
  Scenario: Update user details
    When I create a user with email "updateuser@example.com", given name "Update", family name "User"
    Then the response status should be 201
    When I update the user with given name "Updated", family name "Name"
    Then the response status should be 200
    And the response should contain user with name "Updated Name"

  @delete-user
  Scenario: Delete a user
    When I create a user with email "deleteuser@example.com", given name "Delete", family name "User"
    Then the response status should be 201
    When I delete the created user
    Then the response status should be 204

  @validation
  Scenario: Cannot create user with existing email
    When I create a user with email "admin@briefbot.com", given name "Existing", family name "User"
    Then the response status should be 400
    And the response should contain error message "already exists"

  @validation
  Scenario: Cannot update user with existing email
    When I create a user with email "unique1@example.com", given name "Unique1", family name "User"
    Then the response status should be 201
    When I create a user with email "unique2@example.com", given name "Unique2", family name "User"
    Then the response status should be 201
    When I update the second user with email "unique1@example.com"
    Then the response status should be 400
    And the response should contain error message "already exists"
