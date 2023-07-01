package com.pat.soe.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    public static final String INVALID_JWT = "Invalid JWT";

    private final JwtDecoder decoder;


    @Value("${app.jwtName}")
    private String jwtName;

    @Autowired
    public JwtUtils(JwtDecoder decoder) {
        this.decoder = decoder;
    }

    public Cookie createCookieWithJwt(String token, int maxEge) {
        Cookie cookie = new Cookie(jwtName, token);
        cookie.setPath("/api");
        cookie.setMaxAge(maxEge);
        return cookie;
    }

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie[] cookieArray = request.getCookies();
        if (cookieArray != null) {
            for (Cookie cookie : cookieArray) {
                if (cookie.getName().equals(jwtName))
                    return cookie.getValue();
            }
        }
        return null;
    }

    public Cookie getCleanJwtCookie() {
        Cookie cookie = new Cookie(jwtName, null);
        cookie.setPath("/api");
        cookie.setMaxAge(0);
        return cookie;
    }

    public String getUserNameFromJwtToken(String token) {
        return decoder.decode(token).getClaims().get("sub").toString();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            decoder.decode(authToken);
            return true;
        } catch (JwtException e) {
            throw new JwtException(INVALID_JWT);
        }
    }
}
