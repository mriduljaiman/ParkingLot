package com.mindfire.parkinglot.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.mindfire.parkinglot.exception.GlobalExceptionHandler;

public class GlobalExceptionHandlerTest {

	 private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

	    @Test
	    void testHandleRuntimeException() {
	        RuntimeException exception = new RuntimeException("Error occurred");

	        ResponseEntity<String> response = globalExceptionHandler.handleException(exception);

	        assertEquals(400, response.getStatusCodeValue());
	        assertEquals("Error occurred", response.getBody());
	    }
}
