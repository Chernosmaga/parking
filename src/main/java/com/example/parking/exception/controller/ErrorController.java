package com.example.parking.exception.controller;

import com.example.parking.exception.AlreadyExistsException;
import com.example.parking.exception.NotFoundException;
import com.example.parking.exception.model.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Exception's handler
 */
@RestControllerAdvice
public class ErrorController {

    /**
     * Handles exception if data wasn't found
     * @param exception receives not found exception
     * @return custom entity for exception with helpful information about error
     */
    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException exception) {
        return new ErrorResponse(exception.getMessage(), LocalDateTime.now(), "Data is invalid");
    }

    /**
     * Handles exception if data already exists
     * @param exception receives already exist exception
     * @return custom entity for exception with helpful information about error
     */
    @ExceptionHandler
    @ResponseStatus(CONFLICT)
    public ErrorResponse handleAlreadyExistsException(final AlreadyExistsException exception) {
        return new ErrorResponse(exception.getMessage(), LocalDateTime.now(), "Data conflict");
    }
}
