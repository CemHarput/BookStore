package com.example.bookStore.service;


import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Service;

@Service
public class RateLimitingService {

    private final RateLimiter rateLimiter = RateLimiter.create(10.0); // 10 requests per second

    public boolean tryAcquire() {
        return rateLimiter.tryAcquire();
    }
}
