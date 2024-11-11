package com.mindfire.parkinglot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mindfire.parkinglot.constant.Constants;
import com.mindfire.parkinglot.model.AuthResponse;
import com.mindfire.parkinglot.util.JwtUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/auth")
@Api(value = "Authentication Controller", tags = "Authentication")
public class AuthController {
	
	@Value("${auth.username}")
    private String username;

    @Value("${auth.password}")
    private String password;

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil; 
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
    
    /**
     * login API to authenticate a user and generate JWT token
     * 
     * @param username - admin
     * @param password - password123
     * @return ResponseEntity - contains the JWT token or error message
     */
    @Operation(summary = "User login", description = "Logs in the user and generates a JWT token")
    @ApiResponses(value = {
            @ApiResponse(code = Constants.SUCCESS_CODE, message = Constants.LOGIN_SUCCESS, response = String.class),
            @ApiResponse(code = Constants.UNAUTHORIZED_CODE, message = Constants.INVALID_CREDENTIALS)
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Parameter(description = "Username of the user for login", example = "admin") @RequestParam String username, 
    		@Parameter(description = "Password of the user for login", example = "password123") @RequestParam String password) {
        if (username.equals(username) && password.equals(password)) {
            String token = jwtUtil.generateToken(username);
            logger.info(Constants.JWT_LOG_MESSAGE + token);
            
            //response dto to send token
            AuthResponse authResponse = new AuthResponse(token);
            return ResponseEntity.ok(authResponse);
        } else {
            return ResponseEntity.status(Constants.UNAUTHORIZED_CODE).body(Constants.INVALID_CREDENTIALS);
        }
    }
    
}
