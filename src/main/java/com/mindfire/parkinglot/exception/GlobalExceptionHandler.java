package com.mindfire.parkinglot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mindfire.parkinglot.model.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(SlotNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSlotNotFoundException(SlotNotFoundException ex) {
        // Extract the request path from the current request
        String path = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI();

        // Create and return the ErrorResponse with 404 status
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                path
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleGenericException(Exception e) {
	        return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    
	    @ExceptionHandler(RateLimitException.class)
	    public ResponseEntity<ErrorResponse> handleRateLimitException(RateLimitException ex) {
	        // Extract the request path from the current request
	        String path = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI();

	        // Create and return the ErrorResponse with 429 status
	        ErrorResponse errorResponse = new ErrorResponse(
	                HttpStatus.TOO_MANY_REQUESTS.value(),
	                HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase(),
	                ex.getMessage(),
	                path
	        );

	        return new ResponseEntity<>(errorResponse, HttpStatus.TOO_MANY_REQUESTS);
	    }
	    
	    @ExceptionHandler(RuntimeException.class)
	    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
	        // Extract the request path from the current request
	        String path = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI();

	        // Create and return the ErrorResponse
	        ErrorResponse errorResponse = new ErrorResponse(
	                HttpStatus.INTERNAL_SERVER_ERROR.value(),
	                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
	                ex.getMessage(),
	                path
	        );

	        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
