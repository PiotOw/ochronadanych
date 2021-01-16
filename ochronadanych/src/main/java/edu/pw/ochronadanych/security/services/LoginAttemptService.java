package edu.pw.ochronadanych.security.services;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class LoginAttemptService {

    private final int MAX_ATTEMPT = 3;
    private LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptService() {
        super();
        attemptsCache = CacheBuilder.newBuilder().
                expireAfterWrite(10, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            public Integer load(String key) {
                return 0;
            }
        });
    }

    public void loginSucceeded(String key) throws ExecutionException {
        log.info("login succeed!   last counter value:" + attemptsCache.get(key));
        attemptsCache.invalidate(key);
    }

    public void loginFailed(String key) throws ExecutionException {
        int attempts = 0;
        attempts = attemptsCache.get(key);
        attempts++;
        attemptsCache.put(key, attempts);
        log.info("FAILED TO LOGIN, counter:" + attemptsCache.get(key));
    }

    public boolean isBlocked(String key) throws ExecutionException {
        return attemptsCache.get(key) >= MAX_ATTEMPT;
    }

    public Integer getInvalidLoginCount(String key) throws ExecutionException {
        return attemptsCache.get(key);
    }
}
