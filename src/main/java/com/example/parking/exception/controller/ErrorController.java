package com.example.parking.exception.controller;

import com.example.parking.exception.AlreadyExistsException;
import com.example.parking.exception.NotFoundException;
import com.example.parking.exception.model.ErrorResponse;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

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
    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleNotFoundException(final Exception exception) {
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

    /**
     * Handles exception if data is not valid
     * @param exception receives MethodArgumentNotValidException, HttpMessageNotReadableException
     * @return custom entity for exception with helpful information about error
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class})
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleBadRequestExceptions(final Exception exception) {
        return new ErrorResponse(exception.getMessage(), LocalDateTime.now(), "Data is invalid");
    }
}