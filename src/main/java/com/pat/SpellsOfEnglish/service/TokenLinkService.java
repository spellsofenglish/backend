package com.pat.SpellsOfEnglish.service;

public interface TokenLinkService {
    String generateToken(int seconds);

    String generate2FAToken();

    String generateQR(String secret);

    void activate(String token);
}
