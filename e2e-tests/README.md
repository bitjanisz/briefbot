# BriefBot E2E Tests

End-to-End API tests for BriefBot using Cucumber and REST Assured.

## Prerequisites

- Java 21+
- Maven 3.8+
- BriefBot application running locally on `http://localhost:8080`

## AI Chat Feature - E2E Tests

### Overview
This feature tests the AI Chat functionality that integrates with Ollama to provide intelligent responses about case studies and services. The AI analyzes customer requests and suggests relevant services based on existing case studies, including service IDs and discount information.

### Prerequisites
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

### Features Tested

#### Core Functionality
- **Customer Request Analysis**: AI analyzes customer messages and provides relevant service suggestions
- **Case Study Context**: AI uses existing case studies as context for recommendations
- **Service Suggestions**: Returns suggested services with IDs, names, discount percentages, and final prices
- **Multi-language Support**: Supports responses in different languages (English, Polish, Spanish, French, German)

#### Test Data Coverage
The tests include comprehensive test data covering:
- **Web Development Services**: E-commerce platforms, SaaS applications
- **Mobile App Development**: iOS/Android healthcare and business apps
- **Cloud Migration Services**: Enterprise cloud infrastructure migration
- **AI Integration Services**: Machine learning and analytics platforms
- **IT Consulting Services**: Strategic planning and digital transformation
- **Industry-Specific Solutions**: Healthcare, Finance, Technology sectors
- **Budget Ranges**: Small projects to large enterprise solutions

### API Endpoint
```
POST /api/ai/chat
```

#### Request Format
```json
{
  "message": "I need a website for my e-commerce business",
  "language": "en"
}
```

#### Response Format
```json
{
  "response": "Based on your request for an e-commerce website, I recommend our Web Development Service...",
  "suggestedServices": [
    {
      "serviceId": 1,
      "serviceName": "Web Development Service",
      "discountPercentage": 10.00,
      "finalPrice": 13500.00
    }
  ]
}
```

### Test Scenarios

#### Service-Specific Tests
- **Web Development**: E-commerce, SaaS, business websites
- **Mobile Applications**: Healthcare, business, consumer apps
- **Cloud Migration**: Enterprise system migration
- **AI Integration**: Analytics, machine learning solutions
- **IT Consulting**: Strategic planning and advisory

#### Industry-Specific Tests
- **Healthcare**: Patient management, telemedicine solutions
- **Finance**: Banking systems, secure cloud migration
- **Technology**: AI platforms, SaaS products

#### Budget-Based Tests
- **Small Budget**: Cost-effective solutions
- **Enterprise Level**: Comprehensive digital transformations

#### Language Support
- **English** (default)
- **Polish** (pl)
- **Spanish** (es)
- **French** (fr)
- **German** (de)

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

### Run all AI chat tests
```bash
mvn test -Dtest=CucumberTest -Dcucumber.filter.tags="@ai-chat"
```

### Run specific service type tests
```bash
mvn test -Dtest=CucumberTest -Dcucumber.filter.tags="@chat-web-development"
mvn test -Dtest=CucumberTest -Dcucumber.filter.tags="@chat-mobile-app"
mvn test -Dtest=CucumberTest -Dcucumber.filter.tags="@chat-cloud-migration"
```

### Run industry-specific tests
```bash
mvn test -Dtest=CucumberTest -Dcucumber.filter.tags="@chat-industry-healthcare"
mvn test -Dtest=CucumberTest -Dcucumber.filter.tags="@chat-industry-finance"
```

### Run language-specific tests
```bash
mvn test -Dtest=CucumberTest -Dcucumber.filter.tags="@chat-language"
```

## Test Configuration

Edit `src/test/resources/test.properties` to configure:
- Base URL
- Authentication
- Timeouts
- Test data

## Test Data Structure

### Services Created
1. **Web Development Service** - Complete web applications
2. **Mobile App Development** - Native iOS/Android apps
3. **Cloud Migration Service** - Infrastructure migration
4. **AI Integration Service** - ML/AI capabilities
5. **IT Consulting Service** - Expert advisory (hourly)

### Case Studies Created
1. **E-commerce Platform Development** - Technology sector
2. **Healthcare Mobile App** - Healthcare sector
3. **Financial Services Cloud Migration** - Finance sector
4. **AI-Powered Analytics Platform** - Technology sector
5. **SaaS Product Launch** - Technology sector

## Reports

After running tests, reports are generated in:
- `target/cucumber-reports/`
- Console output with test results

## Notes

1. **AI Response Variability**: AI responses are non-deterministic. Tests focus on structural validation rather than exact content matching.

2. **Service Suggestions**: The AI suggests services based on case study context. Current implementation returns services from the first case study as a baseline.

3. **Discount Calculation**: Discounts are applied based on case study service relationships in the database.

4. **Language Detection**: Language parameter controls response language. AI may not perfectly follow language instructions depending on model capabilities.

5. **Test Data Dependencies**: Tests create their own data to ensure consistency and avoid external dependencies.

## Troubleshooting

### Ollama Connection Issues
```
Error: Connection refused
```
**Solution**: Ensure Ollama is running:
```bash
docker ps | grep ollama
```

### Model Not Available
```
Error: model not found
```
**Solution**: Pull the required model:
```bash
docker exec -it ollama ollama pull llama3.2
```

### Empty Service Suggestions
If suggested services are empty, check:
- Case studies exist in the database
- Services are properly associated with case studies
- Database filters are correctly applied

### Language Not Working
Language support depends on the Ollama model's multilingual capabilities. Some models may not support all requested languages effectively.
