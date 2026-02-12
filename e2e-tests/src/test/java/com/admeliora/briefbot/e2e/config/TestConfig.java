package com.admeliora.briefbot.e2e.config;

import com.admeliora.briefbot.e2e.support.TestContext;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration for REST Assured and test environment
 */
public class TestConfig {

    private static final Properties properties = new Properties();
    private static RequestSpecification requestSpec;

    static {
        loadProperties();
        setupRestAssured();
    }

    private static void loadProperties() {
        try (InputStream input = TestConfig.class.getClassLoader()
                .getResourceAsStream("test.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            System.err.println("Failed to load test.properties: " + e.getMessage());
        }

        // Override with system properties if provided
        if (System.getProperty("test.baseUrl") != null) {
            properties.setProperty("base.url", System.getProperty("test.baseUrl"));
        }
    }

    private static void setupRestAssured() {
        String baseUrl = properties.getProperty("base.url", "http://localhost:8080");

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        RestAssured.baseURI = baseUrl;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    /**
     * Get request specification with session cookies from test context
     * @param context Test context containing session cookies
     * @return Request specification with cookies
     */
    public static RequestSpecification getRequestSpec(TestContext context) {
        return new RequestSpecBuilder()
                .addRequestSpecification(requestSpec)
                .addCookies(context.getSessionCookiesOrEmpty())
                .build();
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url", "http://localhost:8080");
    }

    public static String getActuatorUrl() {
        return properties.getProperty("actuator.url", "http://localhost:8081/actuator/health");
    }

    public static Long getDefaultAccountId() {
        return Long.parseLong(properties.getProperty("default.account.id", "1"));
    }

    public static Long getDefaultUserId() {
        return Long.parseLong(properties.getProperty("default.user.id", "1"));
    }
}

