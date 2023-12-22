package com.matiaszapillon.elevator.util.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Builder
@Data
public class ApiError {
    private HttpStatus httpStatus;
    private String message;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime timestamp;
}
