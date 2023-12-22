package com.matiaszapillon.elevator.util.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidatorExceptionHandler {

    @ExceptionHandler(BindException.class) //field validators for controllers
    public ResponseEntity<?> handleValidationExceptions(BindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<?> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        ApiErrorRecord apiError = initializeApiErrorRecordResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiErrorRecord apiError = initializeApiErrorRecordResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectKeyCodeException.class)
    public ResponseEntity<?> handleIncorrectKeyCodeException(IncorrectKeyCodeException ex) {
        ApiErrorRecord apiError = initializeApiErrorRecordResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExceededWeightLimitException.class)
    public ResponseEntity<?> handleExceededWeightLimitException(ExceededWeightLimitException ex) {
        ApiErrorRecord apiError = initializeApiErrorRecordResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        ApiErrorRecord apiError = initializeApiErrorRecordResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ApiErrorRecord initializeApiErrorRecordResponse(HttpStatus status, String errorMessage) {
        return new ApiErrorRecord(status, errorMessage, LocalDateTime.now());
    }
    private ApiError initializeApiErrorResponse(HttpStatus status, String errorMessage) {
        return ApiError.builder()
                .httpStatus(status)
                .message(errorMessage)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
