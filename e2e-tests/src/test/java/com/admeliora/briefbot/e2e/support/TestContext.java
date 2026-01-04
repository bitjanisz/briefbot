package com.admeliora.briefbot.e2e.support;

import io.cucumber.spring.ScenarioScope;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Shared context for storing data between test steps
 * Scoped to Cucumber scenario to ensure isolation between scenarios
 */
@Component
@ScenarioScope
public class TestContext {

    @Setter
    @Getter
    private Response lastResponse;

    @Setter
    @Getter
    private Cookies sessionCookies;

    private final Map<String, Object> data = new HashMap<>();

    public void put(String key, Object value) {
        data.put(key, value);
    }

    public Object get(String key) {
        return data.get(key);
    }

    public <T> T get(String key, Class<T> type) {
        Object value = data.get(key);
        if (value == null) {
            return null;
        }
        return type.cast(value);
    }

    public Long getCreatedId(String entityType) {
        return get(entityType + ".id", Long.class);
    }

    public void setCreatedId(String entityType, Long id) {
        put(entityType + ".id", id);
    }

    public void clear() {
        lastResponse = null;
        sessionCookies = null;
        data.clear();
    }

    public boolean contains(String key) {
        return data.containsKey(key);
    }

    public String generateRandomEmail() {
        String randomEmail = "test" + RandomStringUtils.insecure().nextAlphanumeric(5) + "@example.com";
        put("random.email", randomEmail);
        return randomEmail;
    }

    public String getRandomEmail() {
        return get("random.email", String.class);
    }

    /**
     * Get session cookies safely - returns empty cookies if not set
     */
    public Cookies getSessionCookiesOrEmpty() {
        return sessionCookies != null ? sessionCookies : new Cookies();
    }
}

