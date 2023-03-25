package com.pat.soe.token;

public interface TokenLinkService {
    String generateToken(int seconds);

    void activate(String token);
}
