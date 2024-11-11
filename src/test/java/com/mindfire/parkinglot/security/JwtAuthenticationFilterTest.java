package com.mindfire.parkinglot.security;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.FilterChain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.mindfire.parkinglot.util.JwtUtil;

public class JwtAuthenticationFilterTest {

	 	@InjectMocks
	    private JwtAuthenticationFilter jwtAuthenticationFilter;

	    @Mock
	    private JwtUtil jwtUtil;

	    @Mock
	    private FilterChain filterChain;
	    
	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    /**
	     * Test the filter behavior with a valid token.
	     * 
	     * When the token is valid:
	     * - The `JwtAuthenticationFilter` should allow the request to proceed by calling `filterChain.doFilter`.
	     */
	    @Test
	    void testDoFilterWithValidToken() throws Exception {
	    	
	        // Define a valid token and a mock request/response
	        String token = "validToken";
	        MockHttpServletRequest request = new MockHttpServletRequest();
	        MockHttpServletResponse response = new MockHttpServletResponse();
	        
	        // Define the behavior of the JwtUtil mock
	        when(jwtUtil.validateToken(token)).thenReturn(true);
	        when(jwtUtil.extractUsername(token)).thenReturn("admin");

	        // Add the token to the Authorization header of the request
	        request.addHeader("Authorization", "Bearer " + token);
	        
	        // Call the filter method with the mock request and response
	        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

	        // Verify that the filter chain proceeds (no errors occur)
	        verify(filterChain).doFilter(request, response);
	    }

	    /**
	     * Test the filter behavior with an invalid token.
	     * 
	     * When the token is invalid:
	     * - The `JwtAuthenticationFilter` should still pass the request along the filter chain.
	     * - The request will not be authenticated, but the filter chain should continue.
	     */
	    @Test
	    void testDoFilterWithInvalidToken() throws Exception {
	    	
	        // Define an invalid token and a mock request/response
	        String token = "invalidToken";
	        MockHttpServletRequest request = new MockHttpServletRequest();
	        MockHttpServletResponse response = new MockHttpServletResponse();
	        
	        // Define the behavior of the JwtUtil mock
	        when(jwtUtil.validateToken(token)).thenReturn(false);

	        // Add the token to the Authorization header of the request
	        request.addHeader("Authorization", "Bearer " + token);
	        
	        // Call the filter method with the mock request and response
	        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

	        // Verify that the filter chain proceeds even with the invalid token
	        verify(filterChain).doFilter(request, response);
	    }
}
