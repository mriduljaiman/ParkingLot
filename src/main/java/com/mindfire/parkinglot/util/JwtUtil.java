package com.mindfire.parkinglot.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.milliseconds}")
	private int milliseconds;

	/**
	 * 
	 * @param username
	 * @return The generated JWT token as a String.
	 */
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}

	/**
	 * Create a token
	 * 
	 * @param map      of claims
	 * @param username
	 * @return created JWT token.
	 */
	@SuppressWarnings("deprecation")
	public String createToken(Map<String, Object> claims, String username) {
		logger.debug("Creating JWT token for username: {}", username);
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + milliseconds))
				.signWith(SignatureAlgorithm.HS512, secretKey).compact();
	}

	/**
	 * Validates the given JWT token
	 * 
	 * @param token
	 * @return true or false on the basis of its validity
	 */
	public boolean validateToken(String token) {
		try {
			String username = extractUsername(token);

			if ("admin".equals(username)) {
				logger.debug("Token validated successfully for username: {}", username);
				return true;
			} else {
				logger.warn("Invalid username in token: {}", username);
				return false;
			}
		} catch (JwtException | IllegalArgumentException ex) {
			logger.error("Invalid JWT token: {}", ex.getMessage());
			return false;
		}
	}

	/**
	 * extracts the username (subject)
	 * 
	 * @param token
	 * @return username stored in the token.
	 */
	public String extractUsername(String token) {
		try {
			return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
		} catch (JwtException ex) {
			logger.error("Error extracting username from token: {}", ex.getMessage());
			throw ex;
		}

	}

}
