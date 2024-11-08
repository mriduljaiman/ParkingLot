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

	    @Test
	    void testLoginSuccess() {
	        when(jwtUtil.generateToken("admin")).thenReturn("mockedToken");

	        ResponseEntity<?> response = authController.login("admin", "password123");
	        
	        assertEquals(200, response.getStatusCodeValue());
	        assertTrue(response.getBody().toString().contains("mockedToken"));
	    }

	    @Test
	    void testLoginFailure() {
	        ResponseEntity<?> response = authController.login("admin", "wrongPassword");
	        
	        assertEquals(401, response.getStatusCodeValue());
	        assertEquals("Invalid username or password", response.getBody());
	    }

}
