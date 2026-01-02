@offer-version-items @smoke
Feature: Offer Version Item Management
  As a user
  I want to manage items in offer versions
  So that I can specify detailed pricing and services

  Background:
    Given the API is available
    And I login with default user

  @create
  Scenario: Add item to offer version
    Given I have an existing offer version for item operations
    And I have an existing service
    When I add that service as an item to the offer version
    Then the response status should be 201
    And the item should be added with calculated pricing

  @read
  Scenario: Get offer version item by ID
    Given I have an offer version with items
    When I get a specific item by id
    Then the response status should be 200
    And the response should contain the item details

  @update
  Scenario: Update item quantity and price
    Given I have an existing offer version item
    When I update the quantity to 5 and price to 1500.00
    Then the response status should be 200
    And the item should be updated with new totals

  @delete
  Scenario: Remove item from offer version
    Given I have an offer version with an item
    When I delete the item
    Then the response status should be 204
    And the offer version total should be recalculated

  @list
  Scenario: List items for offer version
    Given I have an offer version with multiple items
    When I list all items for that version
    Then the response status should be 200
    And the response should contain all items

  @validation
  Scenario: Cannot add item with invalid service
    Given I have an existing offer version for item operations
    When I try to add an item with non-existent service ID
    Then the response status should be 400

  @edge-case
  Scenario: Automatic total calculation
    Given I have an offer version with items
    When I add a new item with quantity 2 and price 100.00
    Then the offer version total should increase by 200.00
    When I update the item quantity to 3
    Then the offer version total should increase by another 100.00
