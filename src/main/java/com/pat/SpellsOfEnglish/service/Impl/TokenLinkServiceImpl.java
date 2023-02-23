package com.pat.SpellsOfEnglish.service.Impl;

import com.pat.SpellsOfEnglish.data.entity.TokenLink;
import com.pat.SpellsOfEnglish.data.repository.TokenLinkRepository;
import com.pat.SpellsOfEnglish.service.TokenLinkService;
import com.pat.SpellsOfEnglish.service.dto.user.UserDtoForSave;
import com.pat.SpellsOfEnglish.service.exception.NotFoundException;
import com.pat.SpellsOfEnglish.service.exception.SoeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service("linkService")
@RequiredArgsConstructor
public class TokenLinkServiceImpl implements TokenLinkService {
    private static final String TOKEN_NOT_FOUND = "Token not found";
    private static final String APP_NAME = "LMS_APP";
    public static final String TOKEN_NOT_VALID = "Token not valid";
    public static String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";

    private final TokenLinkRepository tokenLinkRepository;
    private final TotpManager totpManager;

    @Override
    public String generateToken(int seconds) {
        TokenLink tokenLink = setTokenLink(seconds, null, false);
        tokenLinkRepository.save(tokenLink);
        return tokenLink.getToken();
    }

    @Override
    public String generate2FAToken() {
        return totpManager.generateSecret();
    }

    @Override
    public String generateQR(String secret) {
        return totpManager.getUriForImage(secret);
    }

    @Override
    public void activate(String token) {
        TokenLink existingToken = tokenLinkRepository.findByEmailToken(token).orElseThrow(() -> {
            throw new NotFoundException(TOKEN_NOT_FOUND);
        });
        if (existingToken.isActive()) {
            throw new SoeException("Token already activated");
        }
        if (isExpired(existingToken)) {
            throw new SoeException("Token expired");
        }
        existingToken.setActive(true);
        tokenLinkRepository.save(existingToken);
    }

    private boolean isExpired(TokenLink token) {
        LocalDateTime expirationTime = token.getCreateTime().plusSeconds(token.getActiveTime());
        return LocalDateTime.now().isAfter(expirationTime);
    }

    private TokenLink setTokenLink(int seconds, UserDtoForSave user, boolean fa) {
        TokenLink tokenLink = new TokenLink();
        tokenLink.setActiveTime(seconds);
        tokenLink.setCreateTime(LocalDateTime.now());
        tokenLink.setToken(generateTokenLink());
        return tokenLink;
    }

    private String generateTokenLink() {
        return UUID.randomUUID().toString();
    }
}
