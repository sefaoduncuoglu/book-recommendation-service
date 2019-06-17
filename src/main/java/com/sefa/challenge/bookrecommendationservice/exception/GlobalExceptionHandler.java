package com.sefa.challenge.bookrecommendationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * The type Global exception handler.
 *
 * @author Sefa Oduncuoglu
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Resource not found exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse errorDetails =
                new ErrorResponse(new Date(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Resource Already Exists exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<?> resourceAlreadyExistsException(
            ResourceAlreadyExistsException ex, WebRequest request) {
        ErrorResponse errorDetails =
                new ErrorResponse(new Date(), HttpStatus.OK.toString(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.OK);
    }

    /**
     * Global exception handler response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorResponse errorDetails =
                new ErrorResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}