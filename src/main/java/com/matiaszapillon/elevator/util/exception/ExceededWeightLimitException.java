package com.matiaszapillon.elevator.util.exception;

public class ExceededWeightLimitException extends RuntimeException {
    public ExceededWeightLimitException(String message) {
        super(message);
    }
}
