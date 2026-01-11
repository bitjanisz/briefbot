#@service-relations @smoke
#Feature: Service Relation Management
#  As a user
#  I want to manage service relations
#  So that I can define dependencies between services
#
#  Background:
#    Given the API is available
#    And I login with default user
#
#  @create
#  Scenario: Create a service relation
#    Given I have two existing services
#    When I create a relation between them with type "DEPENDENCY" and impact "HIGH"
#    Then the response status should be 201
#    And the relation should be created
#
#  @read
#  Scenario: Get service relations for a service
#    Given I have a service with relations
#    When I get relations for that service
#    Then the response status should be 200
#    And the response should contain service relations
#
#  @update @skipped
#  Scenario: Update service relation
#    Given I have an existing service relation
#    When I update the relation type to "ALTERNATIVE"
#    Then the response status should be 200
#    And the relation should be updated
#
#  @delete
#  Scenario: Delete a service relation
#    Given I have an existing service relation
#    When I delete the relation
#    Then the response status should be 204
#
#  @validation
#  Scenario: Cannot create relation with invalid service IDs
#    When I create a relation with invalid service IDs
#    Then the response status should be 400
#
#  @edge-case
#  Scenario: Cannot create circular dependency
#    Given I have services A, B, C
#    And A depends on B, B depends on C
#    When I try to make C depend on A
#    Then the response status should be 400
#    And the response should contain error message "circular dependency"
