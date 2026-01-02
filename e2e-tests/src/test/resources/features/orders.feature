@orders @smoke
Feature: Order Management
  As a user
  I want to manage orders
  So that I can track client purchases

  Background:
    Given the API is available
    And I login with default user

  @create
  Scenario: Create a new order from offer
    Given I have an accepted offer
    When I create an order from that offer
    Then the response status should be 201
    And the order should be created with status "PENDING"

  @read
  Scenario: Get order by ID
    Given I have an existing order
    When I get the order by id
    Then the response status should be 200
    And the response should contain the order details

  @update
  Scenario: Update order status
    Given I have an order in status "PENDING"
    When I update the order status to "IN_PROGRESS"
    Then the response status should be 200
    And the order status should be "IN_PROGRESS"

  @delete @skipped
  Scenario: Cancel an order
    Given I have an order in status "PENDING"
    When I cancel the order
    Then the response status should be 200
    And the order status should be "CANCELLED"

  @list
  Scenario: List orders for account
    When I list all orders for account 1
    Then the response status should be 200
    And the response should contain a list of orders

  @validation
  Scenario: Cannot create order from non-accepted offer
    Given I have an offer in status "DRAFT" for order
    When I try to create an order from that offer
    Then the response status should be 400

  @edge-case
  Scenario: Cannot update completed order
    Given I have an order in status "COMPLETED"
    When I try to update the status
    Then the response status should be 400
    And the response should contain error message "cannot modify completed order"
