package com.rigarchitect.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rigarchitect.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * Custom UserDetails implementation that wraps our User entity
 * Used by Spring Security for authentication and authorization
 */
public class UserPrincipal implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private final Long id;
    @Getter
    private final String name;
    @Getter
    private final String email;
    @Getter
    private final BigDecimal budget;

    @JsonIgnore
    private final String password;

    /**
     * Constructor for UserPrincipal with all required fields.
     */
    public UserPrincipal(Long id, String name, String email, String password, BigDecimal budget) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.budget = budget;
    }

    /**
     * Factory method to create UserPrincipal from User entity.
     */
    public static UserPrincipal build(User user) {
        return new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getBudget()
        );
    }

    /**
     * Returns user authorities (currently empty - no role-based auth implemented).
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }


    /**
     * Returns the user's password for authentication.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username (email) for authentication.
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Account expiration check (always returns true).
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Account lock check (always returns true).
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Credential expiration check (always returns true).
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Account enabled check (always returns true).
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}