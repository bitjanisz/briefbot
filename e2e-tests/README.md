# BriefBot E2E Tests

End-to-End API tests for BriefBot using Cucumber and REST Assured.

## Prerequisites

- Java 21+
- Maven 3.8+
- BriefBot application running locally on `http://localhost:8080`

### AI Chat Local Setup

For AI Chat tests (`@ai-chat`), Ollama must be running locally. Use Docker to set up Ollama:

```bash
# Run Ollama container with GPU support
docker run -d --gpus=all -v ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama

# Pull and run the required model (llama3.2)
docker exec -it ollama ollama run llama3.2
```

Ensure the model supports tool calling (function calling). If using a different model, update `OLLAMA_MODEL` in the application configuration.

## Project Structure

```
briefbot-e2e-tests/
├── src/
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── admeliora/
│       │           └── briefbot/
│       │               └── e2e/
│       │                   ├── RunCucumberTest.java          # Test runner
│       │                   ├── config/                       # Configuration
│       │                   ├── steps/                        # Step definitions
│       │                   ├── support/                      # Support classes
│       │                   └── model/                        # DTOs
│       └── resources/
│           ├── features/                                     # Cucumber feature files
│           ├── cucumber.properties                           # Cucumber config
│           └── test.properties                               # Test configuration
├── pom.xml
└── README.md
```

## Running Tests

### Run all tests
```bash
mvn clean test
```

### Run specific feature
```bash
mvn test -Dcucumber.filter.tags="@accounts"
```

### Run with specific profile
```bash
mvn test -Dtest.baseUrl=http://localhost:8080
```

## Test Configuration

Edit `src/test/resources/test.properties` to configure:
- Base URL
- Authentication
- Timeouts
- Test data

## Feature Coverage

- ✅ Accounts CRUD
- ✅ Clients CRUD
- ✅ Services CRUD
- ✅ Briefings CRUD
- ✅ Offers CRUD
- ✅ Orders CRUD
- ✅ Company Profiles CRUD
- ✅ Case Studies CRUD
- ✅ Users management

## Writing Tests

Feature files use Gherkin syntax:

```gherkin
Feature: Client Management
  
  @clients @smoke
  Scenario: Create a new client
    Given the API is available
    When I create a client with name "Test Client"
    Then the response status should be 201
    And the response should contain the client details
```

## Reports

After running tests, reports are generated in:
- `target/cucumber-reports/`
- Console output with test results
