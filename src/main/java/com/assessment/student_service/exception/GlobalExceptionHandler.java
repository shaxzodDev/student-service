package com.assessment.student_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for REST controllers.
 * Catches and handles exceptions thrown across the application in a centralized way.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ResourceNotFoundException thrown in the application.
     * Returns a 404 NOT FOUND status with the exception message.
     *
     * @param ex the ResourceNotFoundException instance
     * @return ResponseEntity with status 404 and error message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Handles any uncaught RuntimeException.
     * Logs the exception and returns a 500 INTERNAL SERVER ERROR response.
     *
     * @param ex the RuntimeException instance
     * @return ResponseEntity with status 500 and generic error message
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        log.error("RuntimeException while processing the request", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong, please try again later");
    }
}
