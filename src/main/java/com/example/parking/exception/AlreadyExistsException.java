package com.example.parking.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * Exception for situation if data exists at database
 */
@Slf4j
public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(final String message) {
        super(message);
        log.error(message);
    }
}
