package com.mindfire.parkinglot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RateLimitConfig{
	
	   @Value("${rate.limit.requests}")
	    private int requests;

	    @Value("${rate.limit.window.seconds}")
	    private int seconds;
	    
	    public int getRequests() {
	        return requests;
	    }

	    public int getSeconds() {
	        return seconds;
	    }
	    
}