package com.rigarchitect.model.enums;

/**
 * Enum representing types of PC components.
 * Matches the SQL ENUM 'component_type' in the database.
 */
public enum ComponentType {
    CPU,
    GPU,
    RAM,
    SSD,
    HDD,
    Motherboard,
    PSU,
    Case,
    Cooler
}
