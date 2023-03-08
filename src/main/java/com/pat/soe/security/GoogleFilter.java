package com.pat.soe.security;

import com.pat.soe.user.exception.UserException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
@Order(1)
public class GoogleFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            boolean check = oAuth2AuthenticationToken.isAuthenticated();
            String regId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            if(check && regId.equals("google")){
//                response.sendRedirect("api/v1.0/auth/registrationGoogle");
                String name = oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name").toString();
                String email = oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email").toString();
//                response.sendRedirect("/api/v1.0/auth/registrationGoogle");

            }
        } catch (Exception e) {
            new UserException("Cannot set user authentication");
        }

        filterChain.doFilter(request, response);
    }
}
