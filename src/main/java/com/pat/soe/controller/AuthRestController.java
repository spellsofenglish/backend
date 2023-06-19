package com.pat.soe.controller;

import com.pat.soe.dto.user.*;
import com.pat.soe.security.JwtUtils;
import com.pat.soe.service.token.TokenLinkService;
import com.pat.soe.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthRestController implements GlobalController {
    private static final String CONFIRMATION_MESSAGE = "An email has been sent to your email address with a confirmation link.";
    private static final String USER_RECOVERY_SEND_TO_EMAIL = "Token send to Email";
    private static final String USER_UPDATE_SUCCESSFULLY = "User update password successfully";
    public static final String YOU_VE_BEEN_SIGNED_OUT = "You've been signed out!";

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final TokenLinkService tokenLinkService;

    @Autowired
    public AuthRestController(UserService userService, JwtUtils jwtUtils, TokenLinkService tokenLinkService) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.tokenLinkService = tokenLinkService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserDtoForAuth dtoForAuth) {
        String cooke = userService.loginUser(dtoForAuth.email(), dtoForAuth.password());
        UserDtoForResponse dtoForResponse = userService.getByEmail(dtoForAuth.email());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cooke)
                .body(dtoForResponse);
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(YOU_VE_BEEN_SIGNED_OUT);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> performRegistration(@RequestBody UserDtoForSave user) {
        userService.registerUser(user);
        return new ResponseEntity<>(CONFIRMATION_MESSAGE, HttpStatus.OK);
    }

    @GetMapping("/activate/{token}/{userId}")
    public ResponseEntity<?> activateUser(@PathVariable String token, @PathVariable UUID userId) {
        tokenLinkService.activate(token);
        userService.activateUser(userId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("https://spells.hedgi.ru/auth"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("/recoveryPass")
    public ResponseEntity<?> performRecoveryPass(@RequestBody UserDtoForRecoveryPass user) {
        userService.recoveryPassword(user.email());
        return new ResponseEntity<>(USER_RECOVERY_SEND_TO_EMAIL, HttpStatus.OK);
    }

    @GetMapping("/recoveryPass/{token}/{userId}")
    public ResponseEntity<?> recoveryPass(Model model, @PathVariable String token, @PathVariable Long userId) {
        tokenLinkService.activate(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("https://spells.hedgi.ru/reset?" + userId));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody UserDtoForUpdatePass user) {
        userService.changePassword(user.id(), user.newPassword());
        return new ResponseEntity<>(USER_UPDATE_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody UserDtoForUpdatePass user) {
        userService.updatePassword(user.id(), user.oldPassword(), user.newPassword());
        return new ResponseEntity<>(USER_UPDATE_SUCCESSFULLY, HttpStatus.OK);
    }
}