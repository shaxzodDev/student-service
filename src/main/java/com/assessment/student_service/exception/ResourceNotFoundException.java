package com.assessment.student_service.exception;

/**
 * Custom exception to be thrown when a requested resource (e.g., student) is not found.
 * This exception is typically mapped to a 404 NOT FOUND HTTP response by GlobalExceptionHandler.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining why the resource was not found
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
