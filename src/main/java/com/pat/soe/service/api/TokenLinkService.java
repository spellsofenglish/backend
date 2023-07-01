package com.pat.soe.service.api;

import org.springframework.security.core.Authentication;

public interface TokenLinkService {
    String generateToken(Authentication authentication, int seconds);

    String generateToken(int seconds, String username);

    void activate(String token);
}
