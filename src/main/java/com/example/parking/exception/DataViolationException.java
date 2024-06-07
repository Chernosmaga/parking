package com.example.parking.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * Thrown if there is a violation of data
 */
@Slf4j
public class DataViolationException extends RuntimeException {

    public DataViolationException(String message) {
        super(message);
        log.error(message);
    }
}
