package com.example.parking.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * Exception for situation if data wasn't found in database
 */
@Slf4j
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
        log.error(message);
    }
}
