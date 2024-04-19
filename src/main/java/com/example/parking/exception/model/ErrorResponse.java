package com.example.parking.exception.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Custom error class to return via ExceptionHandler
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private LocalDateTime date;
    private String reason;
}
