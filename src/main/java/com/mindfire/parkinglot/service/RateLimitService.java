package com.mindfire.parkinglot.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.mindfire.parkinglot.config.RateLimitConfig;

@Service
public class RateLimitService {
	
	private final RateLimitConfig rateLimitConfig;
	
	private final ConcurrentHashMap<String, RequestWindow> requestMap = new ConcurrentHashMap<>();

	 public RateLimitService(RateLimitConfig rateLimitConfig) {
	        this.rateLimitConfig = rateLimitConfig;
	    }

	    public boolean isAllowed(String clientId) {
	        int allowedRequests = rateLimitConfig.getRequests();
	        int windowSeconds = rateLimitConfig.getSeconds();

	        RequestWindow requestWindow = requestMap.getOrDefault(clientId, new RequestWindow());

	        long currentTime = System.currentTimeMillis();
	        long windowStartTime = currentTime - windowSeconds * 1000L;

	        // Clean up requests that are outside the time window
	        requestWindow.cleanupRequests(windowStartTime);

	        if (requestWindow.getRequestCount() < allowedRequests) {
	            requestWindow.addRequest(currentTime);
	            requestMap.put(clientId, requestWindow);
	            return true; 
	        } else {
	            return false;
	        }
	    }
	    
	private static class RequestWindow {
        private final AtomicInteger requestCount = new AtomicInteger(0);
        private final ConcurrentLinkedQueue<Long> requestTimes = new ConcurrentLinkedQueue<>();

        public void addRequest(long timestamp) {
            requestTimes.add(timestamp);
            requestCount.incrementAndGet();
        }

        public int getRequestCount() {
            return requestCount.get();
        }

        public void cleanupRequests(long windowStartTime) {
            while (!requestTimes.isEmpty() && requestTimes.peek() < windowStartTime) {
                requestTimes.poll();
                requestCount.decrementAndGet();
            }
        }
	}
}
