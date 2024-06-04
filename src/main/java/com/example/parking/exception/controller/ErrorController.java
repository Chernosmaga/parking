package com.example.parking.exception.controller;

import com.example.parking.exception.AlreadyExistsException;
import com.example.parking.exception.DataAccessException;
import com.example.parking.exception.DataViolationException;
import com.example.parking.exception.NotFoundException;
import com.example.parking.exception.model.ErrorResponse;
import jakarta.validation.UnexpectedTypeException;
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
    @ExceptionHandler({DataViolationException.class})
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
            MethodArgumentTypeMismatchException.class, UnexpectedTypeException.class})
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleBadRequestExceptions(final Exception exception) {
        return new ErrorResponse(exception.getMessage(), LocalDateTime.now(), "Data is invalid");
    }

    /**
     * Handles exception if user has no access to data
     * @param exception receives custom exception class DataAccessException
     * @return custom entity for response
     */
    @ExceptionHandler
    @ResponseStatus(FORBIDDEN)
    public ErrorResponse handleDataAccessException(final DataAccessException exception) {
        return new ErrorResponse(exception.getMessage(), LocalDateTime.now(), "Access denied");
    }
}