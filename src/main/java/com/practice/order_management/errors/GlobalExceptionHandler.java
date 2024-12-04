package com.practice.order_management.errors;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Custom Exception handler
    @ExceptionHandler(AppException.class)
    public ResponseEntity<Map<String, String>> AppExceptionHandler(AppException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("status", String.valueOf(ex.getStatusCode()));
        errorMap.put("message", ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(errorMap);
    }

    // Validation Exception handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validationExceptionHandler(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();

        ex.getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMap.put(field, message);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }

    // All other exception handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> allExceptionHandler(Exception ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }
}
