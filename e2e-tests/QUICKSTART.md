# Quick Start Guide - BriefBot E2E Tests

## Setup

1. **Ensure BriefBot is running locally:**
   ```bash
   cd /home/bora/IdeaProjects/briefbot
   mvn spring-boot:run
   ```
   The application should be available at `http://localhost:8080`

2. **Navigate to the test project:**
   ```bash
   cd /home/bora/IdeaProjects/briefbot-e2e-tests
   ```

3. **Build the test project:**
   ```bash
   mvn clean compile
   ```

## Running Tests

### Run all tests
```bash
mvn clean test
```

### Run specific feature
```bash
# Run only client tests
mvn test -Dcucumber.filter.tags="@clients"

# Run only smoke tests
mvn test -Dcucumber.filter.tags="@smoke"

# Run specific scenario
mvn test -Dcucumber.filter.tags="@create"
```

### Run tests against different environment
```bash
mvn test -Dtest.baseUrl=http://localhost:9090
```

## Test Reports

After running tests, reports are available at:
- **HTML Report:** `target/cucumber-reports/cucumber.html`
- **JSON Report:** `target/cucumber-reports/cucumber.json`

Open the HTML report in your browser:
```bash
xdg-open target/cucumber-reports/cucumber.html
```

## Available Feature Files

- `features/accounts.feature` - Account management tests
- `features/clients.feature` - Client CRUD tests
- `features/services.feature` - Service CRUD tests (with relations)

## Test Tags

- `@smoke` - Critical tests that should always pass
- `@clients` - Client-related tests
- `@accounts` - Account-related tests
- `@services` - Service-related tests
- `@create` - Create operations
- `@read` - Read operations
- `@update` - Update operations
- `@delete` - Delete operations
- `@list` - List operations
- `@ignore` - Tests to skip

## Troubleshooting

### Connection refused error
Make sure BriefBot is running on the configured port.

### Tests failing with 401/403
Authentication is currently hardcoded to use accountId=1. Make sure your local database has this account.

### Tests failing with 404
Check that the API endpoints match your actual implementation.

## Extending Tests

### Add a new feature file
1. Create a new `.feature` file in `src/test/resources/features/`
2. Write scenarios using Gherkin syntax
3. Create corresponding step definitions in `src/test/java/.../steps/`

### Add a new step definition
1. Create a new step class or add to existing
2. Use `@Given`, `@When`, `@Then`, `@And` annotations
3. Share data between steps using `TestContext`

Example:
```java
@When("I create a briefing for client {long}")
public void iCreateABriefing(Long clientId) {
    // Implementation
    context.setLastResponse(response);
}
```

## Configuration

Edit `src/test/resources/test.properties` to change:
- `base.url` - API base URL
- `default.account.id` - Default account for tests
- `default.user.id` - Default user for tests

