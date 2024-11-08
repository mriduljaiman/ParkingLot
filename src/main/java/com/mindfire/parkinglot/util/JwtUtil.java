package com.mindfire.parkinglot.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
    private String secretKey;
	
	public String generateToken(String username) {
	    Map<String, Object> claims = new HashMap<>();
	    return createToken(claims, username);
	}
	
	public String createToken(Map<String, Object> claims, String username) {
		System.out.println("secretKey---" + secretKey);
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
	            .setExpiration(new Date(System.currentTimeMillis() + 86400000))
	            .signWith(SignatureAlgorithm.HS512, secretKey)
	            .compact();
	}
	

	public boolean validateToken(String token) {
		try {
	        System.out.println("Validating Token: " + token); 
	        String username = extractUsername(token);
	        System.out.println("Extracted Username: " + username); 

	        return username.equals("admin");
	    } catch (JwtException | IllegalArgumentException ex) {
	        System.err.println("Invalid token: " + ex.getMessage());
	        return false;
	    }
	}
 

	public String extractUsername(String token) {
	    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	  }

}
