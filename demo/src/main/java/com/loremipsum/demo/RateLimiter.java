package com.loremipsum.demo;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@Component
public class RateLimiter implements HandlerInterceptor{
    private final Map<String, Bucket> cache = new HashMap<>();
    private final long REQUESTS_PER_MINUTE = 100;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
            throws Exception {
        
        String clientIp = getClientIp(request);
        Bucket bucket = cache.computeIfAbsent(clientIp, k -> createBucket());

        if (bucket.tryConsume(1)) {
            return true;  // Allow request
        } else {
            response.setStatus(429);
            response.getWriter().write("Rate limit exceeded");
            return false;  // Block request
        }
    }

    private Bucket createBucket() {
        Bandwidth limit = Bandwidth.classic(REQUESTS_PER_MINUTE, 
            Refill.intervally(REQUESTS_PER_MINUTE, Duration.ofMinutes(1)));
        return Bucket.builder().addLimit(limit).build();
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
