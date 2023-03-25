package com.pat.soe.token;

import com.pat.soe.user.exception.UserNotFoundException;
import com.pat.soe.user.exception.UserException;
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
    @Override
    public String generateToken(int seconds) {
        TokenLink tokenLink = setTokenLink(seconds);
        tokenLinkRepository.save(tokenLink);
        return tokenLink.getToken();
    }

    @Override
    public void activate(String token) {
        TokenLink existingToken = tokenLinkRepository.findByEmailToken(token).orElseThrow(() -> {
            throw new UserNotFoundException(TOKEN_NOT_FOUND);
        });
        if (existingToken.isActive()) {
            throw new UserException("Token already activated");
        }
        if (isExpired(existingToken)) {
            throw new UserException("Token expired");
        }
        existingToken.setActive(true);
        tokenLinkRepository.save(existingToken);
    }

    private boolean isExpired(TokenLink token) {
        LocalDateTime expirationTime = token.getCreateTime().plusSeconds(token.getActiveTime());
        return LocalDateTime.now().isAfter(expirationTime);
    }

    private TokenLink setTokenLink(int seconds) {
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
