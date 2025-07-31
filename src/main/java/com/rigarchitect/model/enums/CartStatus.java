package com.rigarchitect.model.enums;

/**
 * Enum representing the status of a build cart.
 * Matches the SQL ENUM 'cart_status' in the database.
 */
public enum CartStatus {
    ACTIVE,
    CHECKED_OUT,
    CANCELLED
}
