package com.example.parking.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * Exception thrown if user is not an owner of the entity
 */
@Slf4j
public class DataAccessException extends RuntimeException {

    public DataAccessException(String message) {
        super(message);
        log.error(message);
    }
}
