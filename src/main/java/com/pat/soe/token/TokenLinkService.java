package com.pat.soe.token;

public interface TokenLinkService {
    String generateToken(int seconds);

    String generate2FAToken();

    String generateQR(String secret);

    void activate(String token);
}
