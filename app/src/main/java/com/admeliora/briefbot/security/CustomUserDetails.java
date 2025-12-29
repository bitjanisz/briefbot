package com.admeliora.briefbot.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Custom UserDetails implementation with additional user attributes
 * Contains userId, accountId, givenName, familyName for JWT authentication
 */
@Getter
public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final boolean enabled;
    private final boolean accountNonExpired;
    private final boolean credentialsNonExpired;
    private final boolean accountNonLocked;
    private final Collection<? extends GrantedAuthority> authorities;

    // Custom fields
    private final Long userId;
    private final Long accountId;
    private final String email;
    private final String givenName;
    private final String familyName;

    /**
     * Constructor for CustomUserDetails
     * Used by JWT authentication filter and form login
     */
    public CustomUserDetails(
            String username,
            String password,
            boolean enabled,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities,
            Long userId,
            Long accountId,
            String email,
            String givenName,
            String familyName
    ) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = authorities;
        this.userId = userId;
        this.accountId = accountId;
        this.email = email;
        this.givenName = givenName;
        this.familyName = familyName;
    }

    /**
     * Simplified constructor - all accounts are enabled and non-expired
     */
    public CustomUserDetails(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            Long userId,
            Long accountId,
            String email,
            String givenName,
            String familyName
    ) {
        this(username, password, true, true, true, true, authorities,
             userId, accountId, email, givenName, familyName);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

