package com.admeliora.briefbot.application.account.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Account Model Tests")
class AccountTest {

    @Test
    @DisplayName("Should create account with builder")
    void shouldCreateAccountWithBuilder() {
        // Given
        String name = "Test Account";
        LocalDateTime createdAt = LocalDateTime.now();

        // When
        Account account = Account.builder()
                .name(name)
                .createdAt(createdAt)
                .build();

        // Then
        assertThat(account.getName()).isEqualTo(name);
        assertThat(account.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    @DisplayName("Should create account with no-args constructor")
    void shouldCreateAccountWithNoArgsConstructor() {
        // When
        Account account = new Account();

        // Then
        assertThat(account.getName()).isNull();
        assertThat(account.getCreatedAt()).isNull();
    }

    @Test
    @DisplayName("Should create account with all-args constructor")
    void shouldCreateAccountWithAllArgsConstructor() {
        // Given
        String name = "Test Account";
        LocalDateTime createdAt = LocalDateTime.now();

        // When
        Account account = new Account(name, createdAt);

        // Then
        assertThat(account.getName()).isEqualTo(name);
        assertThat(account.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    @DisplayName("Should set and get name")
    void shouldSetAndGetName() {
        // Given
        Account account = new Account();
        String name = "New Account Name";

        // When
        account.setName(name);

        // Then
        assertThat(account.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("Should set and get createdAt")
    void shouldSetAndGetCreatedAt() {
        // Given
        Account account = new Account();
        LocalDateTime createdAt = LocalDateTime.now();

        // When
        account.setCreatedAt(createdAt);

        // Then
        assertThat(account.getCreatedAt()).isEqualTo(createdAt);
    }
}
