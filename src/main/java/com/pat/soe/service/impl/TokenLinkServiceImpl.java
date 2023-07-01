package com.pat.soe.service.impl;

import com.pat.soe.entity.TokenLink;
import com.pat.soe.exception.UserNotFoundException;
import com.pat.soe.exception.UserValidationException;
import com.pat.soe.repository.TokenLinkRepository;
import com.pat.soe.service.api.TokenLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service("linkService")
public class TokenLinkServiceImpl implements TokenLinkService {
    private static final String TOKEN_NOT_FOUND = "Token not found";
    public static final String TOKEN_NOT_VALID = "Token not valid";

    private final TokenLinkRepository tokenLinkRepository;
    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    @Autowired
    public TokenLinkServiceImpl(TokenLinkRepository tokenLinkRepository, JwtEncoder encoder, JwtDecoder decoder) {
        this.tokenLinkRepository = tokenLinkRepository;
        this.encoder = encoder;
        this.decoder = decoder;
    }

    @Override
    public String generateToken(Authentication authentication, int seconds) {
        TokenLink tokenLink = setTokenLink(authentication, seconds);
        tokenLinkRepository.save(tokenLink);
        return tokenLink.getToken();
    }

    @Override
    public String generateToken(int seconds, String username) {
        TokenLink tokenLink = setTokenLinkForRegistration(seconds, username);
        tokenLinkRepository.save(tokenLink);
        return tokenLink.getToken();
    }

    @Override
    public void activate(String token) {
        TokenLink existingToken = tokenLinkRepository.findByEmailToken(token).orElseThrow(() -> {
            throw new UserNotFoundException(TOKEN_NOT_FOUND);
        });
        if (existingToken.isActive()) {
            throw new UserValidationException("Token already activated");
        }
        if (isExpired(existingToken)) {
            throw new UserValidationException("Token expired");
        }
        existingToken.setActive(false);
        tokenLinkRepository.save(existingToken);
    }

    private boolean isExpired(TokenLink token) {
        Instant expirationTime = decoder.decode(token.getToken()).getClaim("expiresAt");
        return Instant.now().isAfter(expirationTime);
    }

    private TokenLink setTokenLink(Authentication authentication, int seconds) {
        TokenLink tokenLink = new TokenLink();
        tokenLink.setToken(generateTokenLinkForLogin(authentication, seconds));
        return tokenLink;
    }

    private TokenLink setTokenLinkForRegistration(int seconds, String username) {
        TokenLink tokenLink = new TokenLink();
        tokenLink.setToken(generateTokenLinkForRegistration(seconds, username));
        tokenLink.setActive(true);
        return tokenLink;
    }

    private String generateTokenLinkForLogin(Authentication authentication, int seconds) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("soe")
                .issuedAt(now)
                .expiresAt(now.plus(seconds, ChronoUnit.SECONDS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private String generateTokenLinkForRegistration(int seconds, String username) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("soe")
                .subject(username)
                .issuedAt(now)
                .expiresAt(now.plus(seconds, ChronoUnit.SECONDS))
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
