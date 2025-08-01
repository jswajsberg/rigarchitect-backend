package com.rigarchitect.model.enums;

/**
 * Enum representing the status of a build cart.
 * Matches the SQL ENUM 'cart_status' in the database.
 */
public enum CartStatus {
    DRAFT,
    ACTIVE,
    FINALIZED,
    CANCELLED
}
