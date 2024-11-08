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

	    @Test
	    void testDoFilterWithValidToken() throws Exception {
	        String token = "validToken";
	        MockHttpServletRequest request = new MockHttpServletRequest();
	        MockHttpServletResponse response = new MockHttpServletResponse();
	        
	        when(jwtUtil.validateToken(token)).thenReturn(true);
	        when(jwtUtil.extractUsername(token)).thenReturn("admin");

	        request.addHeader("Authorization", "Bearer " + token);
	        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

	        verify(filterChain).doFilter(request, response);
	    }

	    @Test
	    void testDoFilterWithInvalidToken() throws Exception {
	        String token = "invalidToken";
	        MockHttpServletRequest request = new MockHttpServletRequest();
	        MockHttpServletResponse response = new MockHttpServletResponse();
	        
	        when(jwtUtil.validateToken(token)).thenReturn(false);

	        request.addHeader("Authorization", "Bearer " + token);
	        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

	        verify(filterChain).doFilter(request, response);
	    }
}
