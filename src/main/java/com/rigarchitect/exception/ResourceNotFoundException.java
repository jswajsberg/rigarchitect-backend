package com.rigarchitect.exception;

/**
 * Exception thrown when a requested resource cannot be found.
 */
public class ResourceNotFoundException extends RuntimeException {
    
    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
