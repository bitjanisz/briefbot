@ai-chat @smoke
Feature: AI Chat with Case Studies Context
  As a user
  I want to interact with AI assistant
  So that I can get insights about case studies and services

  Background:
    Given the API is available
    And I login with default user

  @chat-simple
  Scenario: Send a simple message to AI
    When I send a chat message "Suggest me services for my business"
    Then the response status should be 200
    And the chat response should not be empty

  Scenario: Send a simple message to AI (in Polish)
    When I send a chat message "Suggest me services for my business. Please provide an answer in Polish."
    Then the response status should be 200
    And the chat response should not be empty

  @chat-case-studies
  Scenario: Ask AI about case studies
    Given I have an existing case study with title "AI Integration Project"
    When I send a chat message "Tell me about the available case studies"
    Then the response status should be 200
    And the chat response should not be empty
    And the chat response should contain information about case studies

  @chat-case-studies-with-services
  Scenario: Ask AI about case studies with services
    Given I have an existing case study with title "Cloud Migration" and services
    When I send a chat message "What are the services included in my case studies?"
    Then the response status should be 200
    And the chat response should not be empty

  @chat-specific-case-study
  Scenario: Ask AI about a specific case study
    Given I have an existing case study with title "E-commerce Platform"
    When I send a chat message "Give me details about the E-commerce Platform case study"
    Then the response status should be 200
    And the chat response should not be empty

  @chat-pricing
  Scenario: Ask AI about pricing for services in case studies
    Given I have case studies with services and pricing
    When I send a chat message "What is the total pricing for all case study services?"
    Then the response status should be 200
    And the chat response should not be empty

  @chat-empty-message
  Scenario: Send empty message to AI
    When I send an empty chat message
    Then the response status should be 400

  @chat-long-message
  Scenario: Send a long message to AI
    When I send a very long chat message
    Then the response status should be 200
    And the chat response should not be empty

  @chat-function-calling
  Scenario: Verify AI can access case study data via function calling
    Given I have multiple case studies with services
    When I send a chat message "List all case studies and their associated services with prices"
    Then the response status should be 200
    And the chat response should not be empty
    And the chat response should mention case study names

