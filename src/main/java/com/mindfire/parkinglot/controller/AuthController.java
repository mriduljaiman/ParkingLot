package com.mindfire.parkinglot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mindfire.parkinglot.util.JwtUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/auth")
@Api(value = "Authentication Controller", tags = "Authentication")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    
    private static final String STATIC_USERNAME = "admin";
    private static final String STATIC_PASSWORD = "password123";
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
    /**
     * 
     * @param username - admin
     * @param password - password123
     * @return
     */
    @ApiOperation(value = "User login", notes = "Logs in the user and generates a JWT token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login successful, returns JWT token", response = String.class),
            @ApiResponse(code = 401, message = "Invalid username or password")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        if (STATIC_USERNAME.equals(username) && STATIC_PASSWORD.equals(password)) {
            String token = jwtUtil.generateToken(username);
            logger.info(token);
            return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
    
}
