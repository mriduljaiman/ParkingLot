package com.mindfire.parkinglot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import com.mindfire.parkinglot.util.JwtUtil;

public class AuthControllerTest {
	
	 	@InjectMocks
	    private AuthController authController;

	    @Mock
	    private AuthenticationManager authenticationManager;

	    @Mock
	    private JwtUtil jwtUtil;
	    
	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    /**
	     * Test case for successful login.
	     * This will test if the login method successfully generates a token when valid credentials are provided.
	     */
	    @Test
	    void testLoginSuccess() {
	        when(jwtUtil.generateToken("admin")).thenReturn("mockedToken");

	     // Simulating a login with valid credentials
	        ResponseEntity<?> response = authController.login("admin", "password123");
	        
	     // Validate the response
	        assertEquals(200, response.getStatusCodeValue());
	        assertTrue(response.getBody().toString().contains("mockedToken"));
	    }

	    /**
	     * Test case for failed login.
	     * This will test the scenario when invalid credentials are provided and ensure the correct error message is returned.
	     */
	    @Test
	    void testLoginFailure() {
	    	
	    	// Simulating a login with invalid credentials
	        ResponseEntity<?> response = authController.login("admin", "wrongPassword");
	        
	     // Validate the response
	        assertEquals(401, response.getStatusCodeValue());
	        assertEquals("Invalid username or password", response.getBody());
	    }

}
