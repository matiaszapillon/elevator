package com.matiaszapillon.elevator.util.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ApiErrorRecord(HttpStatus httpStatus,
                             String message,
                             @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss") LocalDateTime timeStamp) {
}
