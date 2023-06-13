package com.pat.soe.service.token;

public interface TokenLinkService {
    String generateToken(int seconds);

    void activate(String token);
}
