package com.mindfire.parkinglot.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mindfire.parkinglot.exception.RateLimitException;
import com.mindfire.parkinglot.service.RateLimitService;
import com.mindfire.parkinglot.util.JwtUtil;

@Aspect
@Component
public class RateLimitAspect {
	
	private static final String[] AUTH_WHITELIST = {
//          -- 
			"/swagger-ui/**",
         "/swagger-resources/**",
         "/swagger-ui.html",
         "/v3/api-docs/**",
         "/v2/api-docs/**",
         "/swagger-config",
         "/webjars/**",
         "/swagger",
 };

	
    private final RateLimitService rateLimitService;
    private final JwtUtil jwtUtil;

    public RateLimitAspect(RateLimitService rateLimitService, JwtUtil jwtUtil) {
        this.rateLimitService = rateLimitService;
        this.jwtUtil = jwtUtil;
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
              "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
              "@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void rateLimitedMethods() {
    }

    @Before("rateLimitedMethods()")
    public void checkRateLimit() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String path = request.getRequestURI();
        for (String whitelistPath : AUTH_WHITELIST) {
	        if (path.startsWith(whitelistPath)) {
	            return; 
	        }
        }
        
        String clientId = getClientId(request);

        if (!rateLimitService.isAllowed(clientId)) {
            throw new RateLimitException("Too many requests. Please try again later.");
        }
    }

    private String getClientId(HttpServletRequest request) {
    	String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return jwtUtil.extractUsername(token);
        }

        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }
    
    
   
}