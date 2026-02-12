@ai-chat @smoke
Feature: AI Chat with Case Studies Context
  As a user
  I want to interact with AI assistant
  So that I can get insights about case studies and services

  Background:
    Given the API is available
    And I login with default user

  @customer-retail-ecommerce
  Scenario: Retail customer wants e-commerce website
    Given I have case studies with services and pricing
    When I send a chat message "I'm a traditional retail store owner and want to move online.\nHow can I create an e-commerce website with payment integration?\nI currently sell clothing and accessories in a physical store but want to expand my reach.\nWhat services would you recommend for setting up online sales, inventory management, and customer tracking?"
    Then the response status should be 200
    And the chat response should not be empty
    And the response should include suggested services with IDs and discounts

  @customer-healthcare-clinic
  Scenario: Medical clinic needs mobile app
    Given I have case studies with services and pricing
    When I send a chat message "Prowadzimy klinikę medyczną i chcemy stworzyć aplikację mobilną dla pacjentów do rezerwacji wizyt i dostępu do dokumentacji medycznej.\nNasza obecna sytuacja:\n- Pacjenci muszą dzwonić, aby umówić wizytę, co prowadzi do długich czasów oczekiwania\n- Dokumentacja medyczna jest prowadzona w formie papierowej i trudna do dostępu\n- Chcemy poprawić zaangażowanie pacjentów i zmniejszyć liczbę niepojawień się\nJakie rozwiązanie aplikacji mobilnej polecacie dla placówki medycznej?"
    Then the response status should be 200
    And the chat response should not be empty
    And the response should include suggested services with IDs and discounts

  @customer-finance-bank
  Scenario: Traditional bank needs digital transformation
    Given I have case studies with services and pricing
    When I send a chat message "We're a traditional bank looking to modernize our online banking platform and mobile app.\nCurrent challenges:\n- Our online banking interface is outdated and hard to use\n- Mobile banking app has poor user experience\n- Security concerns with current systems\n- Need API integrations for third-party financial services\nWhat comprehensive digital banking solution would you recommend?"
    Then the response status should be 200
    And the chat response should not be empty
    And the response should include suggested services with IDs and discounts

  @customer-saas-launch
  Scenario: B2B SaaS company needs user acquisition
    Given I have case studies with services and pricing
    When I send a chat message "We're launching a B2B SaaS product and need help with user acquisition and growth strategies.\nProduct details:\n- Cloud-based project management platform for teams\n- Target audience: small to medium businesses\n- Key features: task management, collaboration tools\nWe need professional website, marketing campaigns, and growth strategies.\nCan you help us create a comprehensive launch plan?"
    Then the response status should be 200
    And the chat response should not be empty
    And the response should include suggested services with IDs and discounts

  @customer-manufacturing-iot
  Scenario: Manufacturing plant needs IoT sensors
    Given I have case studies with services and pricing
    When I send a chat message "Nasz zakład produkcyjny potrzebuje czujników IoT i systemów predykcyjnej konserwacji.\nObecna sytuacja:\n- Produkujemy urządzenia i maszyny przemysłowe\n- Częste nieplanowane przestoje z powodu awarii sprzętu\n- Wysokie koszty konserwacji przy podejściu reaktywnym\nPotrzebujemy instalacji czujników IoT, oprogramowania do predykcyjnej konserwacji i analityki danych.\nJakie rozwiązanie transformacji cyfrowej polecacie dla przemysłu?"
    Then the response status should be 200
    And the chat response should not be empty
    And the response should include suggested services with IDs and discounts
